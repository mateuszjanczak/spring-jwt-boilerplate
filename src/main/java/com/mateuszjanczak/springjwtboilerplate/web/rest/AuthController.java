package com.mateuszjanczak.springjwtboilerplate.web.rest;

import com.mateuszjanczak.springjwtboilerplate.dto.request.LoginRequest;
import com.mateuszjanczak.springjwtboilerplate.dto.request.RegisterRequest;
import com.mateuszjanczak.springjwtboilerplate.dto.response.TokenResponse;
import com.mateuszjanczak.springjwtboilerplate.dto.response.UserResponse;
import com.mateuszjanczak.springjwtboilerplate.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthController {

    public static final String PATH_POST_LOGIN = "/account/login";
    public static final String PATH_POST_SIGN_UP = "/account/register";

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(PATH_POST_LOGIN)
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        TokenResponse tokenResponse = authService.login(loginRequest);
        return new ResponseEntity<>(tokenResponse, HttpStatus.OK);
    }

    @PostMapping(PATH_POST_SIGN_UP)
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        UserResponse userResponse = authService.register(registerRequest);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }
}
