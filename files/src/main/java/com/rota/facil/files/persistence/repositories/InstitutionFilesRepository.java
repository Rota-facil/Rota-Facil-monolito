package com.rota.facil.files.persistence.repositories;

import com.rota.facil.files.domain.FileCategory;
import com.rota.facil.files.persistence.entities.BoardPointFilesEntity;
import com.rota.facil.files.persistence.entities.InstitutionFilesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface InstitutionFilesRepository extends JpaRepository<InstitutionFilesEntity, UUID> {
    @Query("""
        SELECT if FROM InstitutionFilesEntity if
        INNER JOIN if.institution i
        INNER JOIN if.prefecture p
        WHERE if.id = :fileId
        AND i.id = :institutionId
        AND p.id = :prefectureId
        AND if.fileCategory = :fileCategory
    """)
    Optional<InstitutionFilesEntity> findByIdAndInstitutionIdAndPrefectureAndFileCategory(@Param(value = "fileId") UUID fileId, @Param(value = "institutionId") UUID institutionId, @Param(value = "prefectureId") UUID prefectureId, @Param(value = "fileCategory") FileCategory fileCategory);

    @Query("""
        SELECT if FROM InstitutionFilesEntity if
        INNER JOIN if.institution i
        INNER JOIN if.prefecture p
        WHERE i.id = :institutionId
        AND p.id = :prefectureId
        AND if.fileCategory = :fileCategory
        ORDER BY if.createdAt DESC
    """)
    List<InstitutionFilesEntity> findAllByInstitutionIdAndPrefectureIdAndFileCategory(@Param(value = "institutionId") UUID institutionId, @Param(value = "prefectureId") UUID prefectureId, @Param(value = "fileCategory") FileCategory fileCategory);
}
