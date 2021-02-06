package com.mateuszjanczak.springjwtboilerplate.service.impl;

import com.mateuszjanczak.springjwtboilerplate.entity.Role;
import com.mateuszjanczak.springjwtboilerplate.entity.RoleName;
import com.mateuszjanczak.springjwtboilerplate.repository.UserRoleRepository;
import com.mateuszjanczak.springjwtboilerplate.service.UserRoleService;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository userRoleRepository;

    public UserRoleServiceImpl(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public Role getRoleUser() {
        return userRoleRepository.findByName(RoleName.ROLE_USER).orElseGet(() -> userRoleRepository.save(new Role(RoleName.ROLE_USER)));
    }

    @Override
    public Role getRoleAdmin() {
        return userRoleRepository.findByName(RoleName.ROLE_ADMIN).orElseGet(() -> userRoleRepository.save(new Role(RoleName.ROLE_ADMIN)));
    }
}
