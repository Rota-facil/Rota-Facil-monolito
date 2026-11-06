package com.rota.facil.security.persistence.repositories;

import com.rota.facil.security.persistence.entities.TokenCompleteGoogleLoginEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface TokenCompleteGoogleLoginRepository extends JpaRepository<TokenCompleteGoogleLoginEntity, UUID> {
    @Query("""
        SELECT tcgl FROM TokenCompleteGoogleLoginEntity tcgl
        INNER JOIN tcgl.user u
        WHERE u.id = :userId
        AND u.active IS TRUE
    """)
    Optional<TokenCompleteGoogleLoginEntity> findByUserId(@Param(value = "userId") UUID userId);

    @Query("""
        SELECT tcgl FROM TokenCompleteGoogleLoginEntity tcgl
        WHERE tcgl.pendingToken = :pendingToken
    """)
    Optional<TokenCompleteGoogleLoginEntity> findByPendingToken(@Param(value = "pendingToken") UUID pendingToken);
}
