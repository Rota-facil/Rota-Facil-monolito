package com.rota.facil.security.repositories;

import com.rota.facil.security.entities.UserTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserTokenRepository extends JpaRepository<UserTokenEntity, UUID> {
    Optional<UserTokenEntity> findByAccessToken(String accessToken);
}
