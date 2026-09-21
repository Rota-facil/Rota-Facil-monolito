package com.rota.facil.users.persistence.repositories;

import com.rota.facil.users.persistence.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByEmailAndActiveTrue(String email);
    Optional<UserEntity> findByIdAndPrefectureId(UUID id, UUID prefectureId);

    @Query("""
        SELECT COUNT(u) FROM UserEntity u
        WHERE u.cpf = :cpf
    """)
    Integer countUsersByCpf(@Param(value = "cpf") String cpf);

    @Query("""
        SELECT COUNT(u) FROM UserEntity u
        WHERE u.email = :email
    """)
    Integer countUsersByEmail(@Param(value = "email") String email);

    @Modifying
    @Query("""
        UPDATE UserEntity u
        SET u.status = com.rota.facil.users.domain.DriverStatus.AVAILABLE
        WHERE u.prefectureId = :prefectureId
        AND u.role = com.rota.facil.users.domain.Role.DRIVER
    """)
    void prepareDriversForDeactivation(@Param("prefectureId") UUID prefectureId);

    @Modifying
    @Query("""
        UPDATE UserEntity u
        SET u.active = false
        WHERE u.prefectureId = :prefectureId
        AND u.role IN (
            com.rota.facil.users.domain.Role.DRIVER,
            com.rota.facil.users.domain.Role.ADMIN
        )
    """)
    void deactivateDriversAndAdminsByPrefectureId(@Param("prefectureId") UUID prefectureId);

    @Modifying(clearAutomatically = true)
    @Query("""
        UPDATE UserEntity u
        SET u.prefectureId = null
        WHERE u.prefectureId = :prefectureId
        AND u.role NOT IN (
            com.rota.facil.users.domain.Role.DRIVER,
            com.rota.facil.users.domain.Role.ADMIN
        )
    """)
    void disassociateNonOperationalUsersByPrefectureId(@Param("prefectureId") UUID prefectureId);
}
