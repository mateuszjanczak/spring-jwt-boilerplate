package com.mateuszjanczak.springjwtboilerplate.service.impl;

import com.mateuszjanczak.springjwtboilerplate.dto.request.LoginRequest;
import com.mateuszjanczak.springjwtboilerplate.dto.request.RegisterRequest;
import com.mateuszjanczak.springjwtboilerplate.dto.response.LoginResponse;
import com.mateuszjanczak.springjwtboilerplate.dto.response.TokenResponse;
import com.mateuszjanczak.springjwtboilerplate.dto.response.UserResponse;
import com.mateuszjanczak.springjwtboilerplate.entity.User;
import com.mateuszjanczak.springjwtboilerplate.entity.UserRefreshToken;
import com.mateuszjanczak.springjwtboilerplate.exception.UserAlreadyExistsException;
import com.mateuszjanczak.springjwtboilerplate.exception.UserNotFoundException;
import com.mateuszjanczak.springjwtboilerplate.exception.WrongPasswordException;
import com.mateuszjanczak.springjwtboilerplate.mapper.UserMapper;
import com.mateuszjanczak.springjwtboilerplate.repository.UserRefreshTokenRepository;
import com.mateuszjanczak.springjwtboilerplate.security.JwtProvider;
import com.mateuszjanczak.springjwtboilerplate.service.AuthService;
import com.mateuszjanczak.springjwtboilerplate.service.UserRoleService;
import com.mateuszjanczak.springjwtboilerplate.service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final UserRoleService userRoleService;
    private final UserRefreshTokenRepository userRefreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public AuthServiceImpl(UserService userService, UserRoleService userRoleService, UserRefreshTokenRepository userRefreshTokenRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider) {
        this.userService = userService;
        this.userRoleService = userRoleService;
        this.userRefreshTokenRepository = userRefreshTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    private LoginResponse doLogin(User user) {
        String token = jwtProvider.createToken(user.getUsername());
        String refreshToken = createRefreshToken(user);
        return new LoginResponse(JwtProvider.TOKEN_PREFIX + token, refreshToken);
    }

    private String createRefreshToken(User user) {
        String refreshToken = RandomStringUtils.randomAlphanumeric(128);
        userRefreshTokenRepository.save(new UserRefreshToken(refreshToken, user));
        return refreshToken;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {

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
            throw new UserAlreadyExistsException();
        }

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRoles(Collections.singletonList(userRoleService.getRoleUser()));

        User persistedUser = userService.save(user);


        return UserMapper.userToResponse(persistedUser);
    }

    @Override
    public User getLoggedUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public Optional<TokenResponse> refreshToken(String refreshToken) {
        return userRefreshTokenRepository.findByToken(refreshToken)
                .map(userRefreshToken -> new TokenResponse(jwtProvider.createToken(userRefreshToken.getUser().getUsername())));
    }
}
