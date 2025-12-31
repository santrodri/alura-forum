package com.alura.forum.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Value("${security.jwt.issuer}")
    private String issuer;
    @Value("${security.jwt.audience}")
    private String audience;
    @Value("${security.jwt.secret}")
    private String secret;

    private JWTVerifier verifier() {
        return JWT.require(Algorithm.HMAC256(secret))
                  .withIssuer(issuer)
                  .withAudience(audience)
                  .acceptLeeway(1)
                  .build();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {
        String auth = req.getHeader("Authorization");
        if (auth != null && auth.startsWith("Bearer ")) {
            String token = auth.substring(7);
            try {
                DecodedJWT jwt = verifier().verify(token);

                List<String> scopes = new ArrayList<>();
                String scopeStr = jwt.getClaim("scope").asString();
                if (StringUtils.hasText(scopeStr)) {
                    scopes.addAll(Arrays.asList(scopeStr.split("\\s+")));
                }

                var authorities = scopes.stream()
                        .map(s -> new SimpleGrantedAuthority("SCOPE_" + s))
                        .collect(Collectors.toList());

                var authentication = new UsernamePasswordAuthenticationToken(jwt.getSubject(), null, authorities);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                org.springframework.security.core.context.SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (Exception ex) {
                res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                res.getWriter().write("Invalid or expired token");
                               return;
            }
        }
        chain.doFilter(req, res);
    }
}
