package com.rota.facil.security.persistence.repositories;

import com.rota.facil.security.persistence.entities.UserTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserTokenRepository extends JpaRepository<UserTokenEntity, UUID> {
    @Query("""
        SELECT ut FROM UserTokenEntity ut
        WHERE ut.accessToken = :accessToken
    """)
    Optional<UserTokenEntity> findByAccessToken(@Param(value = "accessToken") String accessToken);

    @Query("""
        SELECT ut FROM UserTokenEntity ut
        JOIN ut.user u
        WHERE u.id = :userId
    """)
    Optional<UserTokenEntity> findByUserId(@Param(value = "userId") UUID userId);
}
