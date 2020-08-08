package com.mateuszjanczak.springjwtboilerplate.service;

import com.mateuszjanczak.springjwtboilerplate.entity.User;

public interface UserService {
    User loadUserByUsername(String username);
}
