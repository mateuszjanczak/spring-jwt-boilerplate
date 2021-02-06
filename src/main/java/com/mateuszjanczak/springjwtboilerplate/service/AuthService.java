package com.mateuszjanczak.springjwtboilerplate.service;

import com.mateuszjanczak.springjwtboilerplate.dto.request.LoginRequest;
import com.mateuszjanczak.springjwtboilerplate.dto.request.RegisterRequest;
import com.mateuszjanczak.springjwtboilerplate.dto.response.LoginResponse;
import com.mateuszjanczak.springjwtboilerplate.dto.response.TokenResponse;
import com.mateuszjanczak.springjwtboilerplate.dto.response.UserResponse;
import com.mateuszjanczak.springjwtboilerplate.entity.User;

import java.util.Optional;

public interface AuthService {
    LoginResponse login(LoginRequest loginRequest);

    UserResponse register(RegisterRequest registerRequest);

    User getLoggedUser();

    Optional<TokenResponse> refreshToken(String refreshToken);

    void logout(String refreshToken);
}
