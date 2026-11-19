package com.rota.facil.files.persistence.repositories;
import com.rota.facil.files.http.dto.response.FileResponse;
import com.rota.facil.files.persistence.entities.FileEntity;
import com.rota.facil.files.domain.enums.FileCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
public interface FileRepository extends JpaRepository<FileEntity, UUID> {
    @Query("""
        SELECT f FROM FileEntity f
        INNER JOIN f.prefecture p
        INNER JOIN f.creator c
        WHERE p.id = :prefectureId
        AND f.fileCategory = :fileCategory
        AND c.id = :creatorId
    """)
    Optional<FileEntity> findByCreatorIdAndPrefectureIdAndFileCategory(
            @Param(value = "creatorId") UUID creatorId,
            @Param(value = "prefectureId") UUID prefectureId,
            @Param(value = "fileCategory") FileCategory fileCategory
    );

    @Query("""
        SELECT f FROM FileEntity f
        INNER JOIN f.prefecture p
        INNER JOIN f.creator c
        WHERE p.id = :prefectureId
        AND c.id IN :usersId
        AND c.active IS TRUE
    """)
    List<FileEntity> findAllByPrefectureIdAndUsersId(@Param(value = "prefectureId") UUID prefectureId, @Param(value = "usersId") List<UUID> usersId);
}
