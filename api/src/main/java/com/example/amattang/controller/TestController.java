package com.example.amattang.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TestController {

    @GetMapping("/api/any")
    public String any() {
        log.debug("/api/any====");
        return "any";
    }

    @GetMapping("/api/role")
    public String role() {

        return "role";
    }
}
