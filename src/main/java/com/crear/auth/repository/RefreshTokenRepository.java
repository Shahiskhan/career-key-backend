package com.crear.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crear.auth.model.RefreshToken;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
    Optional<RefreshToken> findByJti(String jti);
}
