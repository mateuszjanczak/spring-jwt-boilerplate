package com.mateuszjanczak.springjwtboilerplate.service;

import com.mateuszjanczak.springjwtboilerplate.dto.LoginRequest;
import com.mateuszjanczak.springjwtboilerplate.dto.RegisterRequest;
import com.mateuszjanczak.springjwtboilerplate.security.JwtToken;
import com.mateuszjanczak.springjwtboilerplate.entity.User;

public interface AuthService {

    JwtToken login(LoginRequest loginRequest);
    User register(RegisterRequest registerRequest);

}
