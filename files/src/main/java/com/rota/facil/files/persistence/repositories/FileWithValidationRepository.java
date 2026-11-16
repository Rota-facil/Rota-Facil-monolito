package com.rota.facil.files.persistence.repositories;

import com.rota.facil.files.domain.enums.FileCategory;
import com.rota.facil.files.persistence.entities.FileWithValidationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FileWithValidationRepository extends JpaRepository<FileWithValidationEntity, UUID> {
    @Query("""
        SELECT fwv FROM FileWithValidationEntity fwv
        INNER JOIN fwv.creator c
        INNER JOIN fwv.prefecture p
        WHERE c.id = :creatorId
        AND p.id = :prefectureId
        AND fwv.fileCategory = :fileCategory
        AND fwv.validationStatus != com.rota.facil.files.domain.enums.FileValidationStatus.ANALYSIS
    """)
    List<FileWithValidationEntity> findNotAnalysisByCreatorIdAndPrefectureIdAndFileCategory(@Param(value = "creatorId")  UUID creatorId, @Param(value = "prefectureId") UUID prefectureId, @Param(value = "fileCategory") FileCategory fileCategory);

    @Query("""
        SELECT fwv FROM FileWithValidationEntity fwv
        INNER JOIN fwv.prefecture p
        WHERE fwv.id = :documentId
        AND p.id = :prefectureId
    """)
    Optional<FileWithValidationEntity> findByIdAndPrefectureId(@Param(value = "documentId") UUID documentId, @Param(value = "prefectureId") UUID prefectureId);

    @Query("""
        SELECT fwv FROM FileWithValidationEntity fwv
        INNER JOIN fwv.creator c
        WHERE c.id = :userId
        AND  fwv.fileCategory = com.rota.facil.files.domain.FileCategory.ENROLLMENT_CERTIFICATE
    """)
    Optional<FileWithValidationEntity> findEnrollmentCertificateByUserIdAndFileCategory(@Param(value = "userId") UUID userId);

    @Query("""
        SELECT fwv FROM FileWithValidationEntity fwv
        INNER JOIN fwv.creator c
        WHERE c.id = :userId
        AND  fwv.fileCategory = com.rota.facil.files.domain.FileCategory.CNH
    """)
    Optional<FileWithValidationEntity> findCNHByUserIdAndFileCategory(@Param(value = "userId") UUID userId);

}
