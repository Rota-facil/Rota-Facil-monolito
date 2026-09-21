package com.rota.facil.files.persistence.repositories;
import com.rota.facil.files.persistence.entities.FileEntity;
import com.rota.facil.files.domain.FileCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
public interface FileRepository extends JpaRepository<FileEntity, UUID> {
    List<FileEntity> findAllByOwnerIdAndPrefectureIdAndFileCategoryOrderByCreatedAtDesc(
            UUID ownerId,
            UUID prefectureId,
            FileCategory fileCategory
    );

    Optional<FileEntity> findByIdAndOwnerIdAndPrefectureIdAndFileCategory(
            UUID id,
            UUID ownerId,
            UUID prefectureId,
            FileCategory fileCategory
    );
}
