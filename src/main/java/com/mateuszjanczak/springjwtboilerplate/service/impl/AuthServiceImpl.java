package com.mateuszjanczak.springjwtboilerplate.service.impl;

import com.mateuszjanczak.springjwtboilerplate.dto.request.LoginRequest;
import com.mateuszjanczak.springjwtboilerplate.dto.request.RegisterRequest;
import com.mateuszjanczak.springjwtboilerplate.dto.response.TokenResponse;
import com.mateuszjanczak.springjwtboilerplate.dto.response.UserResponse;
import com.mateuszjanczak.springjwtboilerplate.entity.User;
import com.mateuszjanczak.springjwtboilerplate.exception.UserAlreadyExists;
import com.mateuszjanczak.springjwtboilerplate.exception.UserNotFoundException;
import com.mateuszjanczak.springjwtboilerplate.exception.WrongPasswordException;
import com.mateuszjanczak.springjwtboilerplate.mapper.UserMapper;
import com.mateuszjanczak.springjwtboilerplate.security.JwtProvider;
import com.mateuszjanczak.springjwtboilerplate.service.AuthService;
import com.mateuszjanczak.springjwtboilerplate.service.RoleService;
import com.mateuszjanczak.springjwtboilerplate.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public AuthServiceImpl(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder, JwtProvider jwtProvider) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    private TokenResponse doLogin(User user) {
        String token = jwtProvider.createToken(user.getUsername());
        return new TokenResponse(JwtProvider.TOKEN_PREFIX, token);
    }

    @Override
    public TokenResponse login(LoginRequest loginRequest) {

        return userService.findByUsername(loginRequest.getUsername())
                .map(user -> {
                    if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                        return doLogin(user);
                    } else {
                        throw new WrongPasswordException();
                    }
                }).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public UserResponse register(RegisterRequest registerRequest) {

        Optional<User> existingUser = userService.findByUsername(registerRequest.getUsername());
        if(existingUser.isPresent()){
            throw new UserAlreadyExists();
        }

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRoles(Collections.singletonList(roleService.getRoleUser()));

        User persistedUser = userService.save(user);


        return UserMapper.userToResponse(persistedUser);
    }

    @Override
    public User getLoggedUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication();
    }
}
