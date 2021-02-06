package com.mateuszjanczak.springjwtboilerplate.web.rest;

import com.mateuszjanczak.springjwtboilerplate.security.SecurityService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class ExampleController {

    private final SecurityService securityService;

    public ExampleController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @GetMapping("/guest")
    String guest() {
        return "you have guest access";
    }

    @GetMapping("/user")
    String user() {
        return "you have access for authenticated";
    }

    @GetMapping("/roleUser")
    @PreAuthorize("hasRole('USER')")
    String roleUser() {
        return "you have access for role - user";
    }

    @GetMapping("/roleAdmin")
    @PreAuthorize("hasRole('ADMIN')")
    String roleAdmin() {
        return "you have access for role - admin";
    }

}
