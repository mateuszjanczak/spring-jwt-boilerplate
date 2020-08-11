package com.mateuszjanczak.springjwtboilerplate.repository;

import com.mateuszjanczak.springjwtboilerplate.entity.Role;
import com.mateuszjanczak.springjwtboilerplate.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, String> {

    Optional<Role> findByName(final RoleName roleName);

}
