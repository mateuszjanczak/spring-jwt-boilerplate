package com.mateuszjanczak.springjwtboilerplate.security;

import com.mateuszjanczak.springjwtboilerplate.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    public User getLoggedUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication();
    }

    public Long getLoggedUserId() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication();
        return user.getId();
    }
}
