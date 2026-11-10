package com.rota.facil.files.persistence.repositories;
import com.rota.facil.files.persistence.entities.FileEntity;
import com.rota.facil.files.domain.FileCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    @Query("""
        SELECT f FROM FileEntity f
        WHERE f.ownerId = :ownerId
        AND f.prefectureId = :prefectureId
        AND f.fileCategory = :fileCategory
        AND f.creatorId = :creatorId
    """)
    Optional<FileEntity> findByCreatorIdAndOwnerIdAndPrefectureIdAndFileCategory(
            @Param(value = "creatorId") UUID creatorId,
            @Param(value = "ownerId") UUID ownerId,
            @Param(value = "prefectureId") UUID prefectureId,
            @Param(value = "fileCategory") FileCategory fileCategory
    );
}
