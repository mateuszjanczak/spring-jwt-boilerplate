package com.mateuszjanczak.springjwtboilerplate.dto.response;

import com.mateuszjanczak.springjwtboilerplate.entity.Role;
import com.mateuszjanczak.springjwtboilerplate.entity.RoleName;

import java.util.List;
import java.util.stream.Collectors;

public class UserResponse {

    private String username;
    private String email;
    private List<RoleName> roles;

    public UserResponse() {
    }

    public UserResponse(String username, String email, List<Role> roles) {
        this.username = username;
        this.email = email;
        this.roles = roles.stream().map(Role::getName).collect(Collectors.toList());
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<RoleName> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleName> roles) {
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
