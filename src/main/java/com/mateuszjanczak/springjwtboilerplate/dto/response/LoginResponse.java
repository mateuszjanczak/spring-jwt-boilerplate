package com.mateuszjanczak.springjwtboilerplate.dto.response;

public class LoginResponse {

    private String token;
    private String refreshToken;

    public LoginResponse() {
    }

    public LoginResponse(String token, String refreshToken) {
        this.token = token;
        this.refreshToken = refreshToken;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
