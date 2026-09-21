package com.rota.facil.users.persistence.repositories;

import com.rota.facil.users.persistence.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByEmail(String email);
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
}
