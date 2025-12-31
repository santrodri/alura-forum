package com.alura.forum.services;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

import com.alura.forum.domain.models.User;
import com.alura.forum.repository.UserRepository;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncripter passwordEncripter;

    public boolean saveUser(String username, String password) {
        if (userRepository.findByUsername(username).isPresent()) return false;
        userRepository.save(
            User.builder()
                .username(username)
                .password(passwordEncripter.encrypt(password))
                .build()
            );
        return true;
    }
}
