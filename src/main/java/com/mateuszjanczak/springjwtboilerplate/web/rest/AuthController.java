package com.mateuszjanczak.springjwtboilerplate.web.rest;

import com.mateuszjanczak.springjwtboilerplate.dto.request.LoginRequest;
import com.mateuszjanczak.springjwtboilerplate.dto.request.RefreshTokenRequest;
import com.mateuszjanczak.springjwtboilerplate.dto.request.RegisterRequest;
import com.mateuszjanczak.springjwtboilerplate.dto.response.LoginResponse;
import com.mateuszjanczak.springjwtboilerplate.dto.response.TokenResponse;
import com.mateuszjanczak.springjwtboilerplate.dto.response.UserResponse;
import com.mateuszjanczak.springjwtboilerplate.entity.User;
import com.mateuszjanczak.springjwtboilerplate.exception.InvalidRefreshTokenException;
import com.mateuszjanczak.springjwtboilerplate.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class AuthController {

    public static final String PATH_POST_LOGIN = "/account/login";
    public static final String PATH_POST_SIGN_UP = "/account/register";
    public static final String PATH_POST_REFRESH_TOKEN = "/account/token/refresh";
    public static final String PATH_DELETE_LOGOUT = "/account/logout";
    private static final String PATH_GET_ME = "/account/me";

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(PATH_POST_LOGIN)
    public ResponseEntity<LoginResponse> userPostLogin(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = authService.login(loginRequest);
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    @PostMapping(PATH_POST_SIGN_UP)
    public ResponseEntity<UserResponse> userPostRegister(@Valid @RequestBody RegisterRequest registerRequest) {
        UserResponse userResponse = authService.register(registerRequest);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @PostMapping(PATH_POST_REFRESH_TOKEN)
    public @ResponseBody TokenResponse tokenPostRefresh(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authService.refreshToken(refreshTokenRequest.getRefreshToken()).orElseThrow(InvalidRefreshTokenException::new);
    }

    @DeleteMapping(PATH_DELETE_LOGOUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void tokenDeleteLogout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        authService.logout(refreshTokenRequest.getRefreshToken());
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = PATH_GET_ME, method = RequestMethod.GET)
    public @ResponseBody User tokenGetMe() {
        return authService.getLoggedUser();
    }
}
