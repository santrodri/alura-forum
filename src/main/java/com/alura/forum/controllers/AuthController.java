package com.alura.forum.controllers;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alura.forum.services.TokenService;

import lombok.AllArgsConstructor;


import com.alura.forum.domain.dtos.AuthRequest;
import com.alura.forum.domain.dtos.AuthResponse;
import com.alura.forum.domain.models.User;
import com.alura.forum.repository.UserRepository;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final BCryptPasswordEncoder encoder;

    @PostMapping
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest request) {
        Optional<User> user = userRepository.findByUsername(request.username());
        if (user.isEmpty()) {
            return ResponseEntity.status(401).build();
        }

        if (!encoder.matches(request.password(), user.get().getPassword())) {
             return ResponseEntity.status(401).build();
        }

        String jwt = tokenService.issueToken(user.get().getUsername());
        return ResponseEntity.ok(new AuthResponse(jwt));
    }
}

