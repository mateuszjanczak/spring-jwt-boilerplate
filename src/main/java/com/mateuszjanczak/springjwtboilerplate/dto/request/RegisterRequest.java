package com.mateuszjanczak.springjwtboilerplate.dto.request;

import javax.validation.constraints.NotBlank;

public class RegisterRequest {

    @NotBlank(message = "Email cannot be blank")
    private String email;

    @NotBlank(message = "Username cannot be blank")
    private String username;

    @NotBlank(message = "Password cannot be blank")
    private String password;

    public RegisterRequest() {
    }

    public RegisterRequest(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
