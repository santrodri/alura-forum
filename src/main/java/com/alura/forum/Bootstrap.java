package com.alura.forum;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alura.forum.repository.UserRepository;
import com.alura.forum.services.UserService;

@Configuration
public class Bootstrap {
    @Value("${DEFAULT_USER:admin}")
    private String defaultUser;

    @Value("${DEFAULT_PASS:admin123}")
    private String defaultPass;

    @Bean
    CommandLineRunner initUsers(UserRepository userRepository, UserService userService) {
        return args -> {
    
            if (userRepository.findByUsername(defaultUser).isEmpty()) {
                userService.saveUser(defaultUser, defaultPass);
            }
        };
    };
}