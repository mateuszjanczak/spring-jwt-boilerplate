package com.mateuszjanczak.springjwtboilerplate.service;

import com.mateuszjanczak.springjwtboilerplate.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByUsername(String username);

    User save(User user);

    User loadUserByUsername(String username);
}
