package com.mateuszjanczak.springjwtboilerplate.repository;

import com.mateuszjanczak.springjwtboilerplate.entity.Role;
import com.mateuszjanczak.springjwtboilerplate.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByName(final RoleName roleName);
}
