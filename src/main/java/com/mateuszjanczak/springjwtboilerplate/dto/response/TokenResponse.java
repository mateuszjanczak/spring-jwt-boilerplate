package com.mateuszjanczak.springjwtboilerplate.dto.response;

public class TokenResponse {

    private String prefix;
    private String token;

    public TokenResponse() {
    }

    public TokenResponse(String prefix, String token) {
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

}
