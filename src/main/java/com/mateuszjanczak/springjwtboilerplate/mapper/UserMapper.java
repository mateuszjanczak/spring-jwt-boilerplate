package com.mateuszjanczak.springjwtboilerplate.mapper;

import com.mateuszjanczak.springjwtboilerplate.dto.response.UserResponse;
import com.mateuszjanczak.springjwtboilerplate.entity.Role;
import com.mateuszjanczak.springjwtboilerplate.entity.User;

import java.util.stream.Collectors;

public class UserMapper {

    public static UserResponse userToResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setUsername(user.getUsername());
        userResponse.setEmail(user.getEmail());
        userResponse.setRoles(user.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
        return userResponse;
    }
}
