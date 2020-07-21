package com.mateuszjanczak.springjwtboilerplate.security;

import com.mateuszjanczak.springjwtboilerplate.dto.TokenResponse;

public class JwtToken {

    private String prefix;
    private String token;

    public JwtToken() {
    }

    public JwtToken(String token) {
        this.prefix = JwtProvider.TOKEN_PREFIX;
        this.token = token;
    }

    public JwtToken(String prefix, String token) {
        this.prefix = prefix;
        this.token = token;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String toHeader() {
        return prefix + token;
    }

    public TokenResponse toResponse() {
        return new TokenResponse(prefix, token);
    }

}
