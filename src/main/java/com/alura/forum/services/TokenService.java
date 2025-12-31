
package com.alura.forum.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Value("${security.jwt.secret}")
    private String secret;
    @Value("${security.jwt.issuer}")
    private String issuer;
    @Value("${security.jwt.audience}")
    private String audience;

    public String issueToken(String subject) {
        Algorithm alg = Algorithm.HMAC256(secret);
        long now = System.currentTimeMillis();
        return JWT.create()
                .withIssuer(issuer)
                .withAudience(audience)
                .withSubject(subject)
                .withIssuedAt(new Date(now))
                .withExpiresAt(new Date(now + 60 * 60 * 1000)) // 1h
                .sign(alg);
    }
}
