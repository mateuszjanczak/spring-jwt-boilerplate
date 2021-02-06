package com.mateuszjanczak.springjwtboilerplate.repository;

import com.mateuszjanczak.springjwtboilerplate.entity.UserRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRefreshTokenRepository extends JpaRepository<UserRefreshToken, String> {
    Optional<UserRefreshToken> findByToken(String token);
}
