package com.mateuszjanczak.springjwtboilerplate.service.impl;

import com.mateuszjanczak.springjwtboilerplate.entity.User;
import com.mateuszjanczak.springjwtboilerplate.repository.UserRepository;
import com.mateuszjanczak.springjwtboilerplate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User loadUserByUsername(String username) {
        return findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
