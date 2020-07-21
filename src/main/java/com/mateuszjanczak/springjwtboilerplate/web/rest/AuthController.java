package com.mateuszjanczak.springjwtboilerplate.web.rest;

import com.mateuszjanczak.springjwtboilerplate.dto.LoginRequest;
import com.mateuszjanczak.springjwtboilerplate.dto.RegisterRequest;
import com.mateuszjanczak.springjwtboilerplate.dto.TokenResponse;
import com.mateuszjanczak.springjwtboilerplate.security.JwtProvider;
import com.mateuszjanczak.springjwtboilerplate.security.JwtToken;
import com.mateuszjanczak.springjwtboilerplate.service.AuthService;
import com.mateuszjanczak.springjwtboilerplate.dto.UserResponse;
import com.mateuszjanczak.springjwtboilerplate.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        JwtToken token = authService.login(loginRequest);
        response.addHeader(JwtProvider.AUTHORIZATION_HEADER, token.toHeader());
        return new ResponseEntity<>(token.toResponse(), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        User user = authService.register(registerRequest);
        return new ResponseEntity<>(user.toResponse(), HttpStatus.OK);
    }

}
