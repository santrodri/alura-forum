package com.alura.forum.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/wellcome")
public class Wellcome {
    @GetMapping
    public String getStatus() {
        return new String("Wellcome to Forum API");
    }
    
}
