package com.mateuszjanczak.springjwtboilerplate.web.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class ExampleController {

    @GetMapping("/guest")
    String guest() {
        return "unauthenticated user";
    }

    @GetMapping("/user")
    String user() {
        return "authenticated user";
    }

    @GetMapping("/roleUser")
    @PreAuthorize("hasRole('USER')")
    String roleUser() {
        return "hello user";
    }

    @GetMapping("/roleAdmin")
    @PreAuthorize("hasRole('ADMIN')")
    String roleAdmin() {
        return "hello admin";
    }

}
