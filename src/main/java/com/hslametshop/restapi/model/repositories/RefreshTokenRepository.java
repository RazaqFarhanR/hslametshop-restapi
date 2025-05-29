package com.hslametshop.restapi.model.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.hslametshop.restapi.model.entities.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    void deleteByToken(String token);
}