package com.mateuszjanczak.springjwtboilerplate.service.impl;

import com.mateuszjanczak.springjwtboilerplate.entity.Role;
import com.mateuszjanczak.springjwtboilerplate.entity.RoleName;
import com.mateuszjanczak.springjwtboilerplate.repository.RoleRepository;
import com.mateuszjanczak.springjwtboilerplate.service.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role getRoleUser() {
        return roleRepository.findByName(RoleName.role_user).orElseGet(() -> roleRepository.save(new Role(RoleName.role_user)));
    }
}
