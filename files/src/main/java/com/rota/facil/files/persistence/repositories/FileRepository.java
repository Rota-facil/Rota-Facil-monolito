package com.rota.facil.files.persistence.repositories;
import com.rota.facil.files.persistence.entities.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
public interface FileRepository extends JpaRepository<FileEntity, UUID> {}
