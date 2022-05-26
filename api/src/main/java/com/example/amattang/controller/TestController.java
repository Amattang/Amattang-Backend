package com.example.amattang.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/api/any")
    public String any() {
        return "any";
    }

    @GetMapping("/api/role")
    public String role() {

        return "role";
    }
}
