package com.rota.facil.files.persistence.repositories;

import com.rota.facil.files.persistence.entities.DocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DocumentRepository extends JpaRepository<DocumentEntity, UUID> {
}
