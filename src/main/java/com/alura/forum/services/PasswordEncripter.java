package com.alura.forum.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;


@Component
@AllArgsConstructor
public class PasswordEncripter {
    private final BCryptPasswordEncoder encoder;

    public String encrypt(String rawPassword) {
        return encoder.encode(rawPassword);
    }
}
