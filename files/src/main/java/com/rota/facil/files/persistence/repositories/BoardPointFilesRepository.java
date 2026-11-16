package com.rota.facil.files.persistence.repositories;

import com.rota.facil.files.domain.enums.FileCategory;
import com.rota.facil.files.persistence.entities.BoardPointFilesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BoardPointFilesRepository extends JpaRepository<BoardPointFilesEntity, UUID> {
    @Query("""
        SELECT bf FROM BoardPointFilesEntity bf
        INNER JOIN bf.boardPoint b
        INNER JOIN bf.prefecture p
        WHERE bf.id = :fileId
        AND b.id = :boardPointId
        AND p.id = :prefectureId
        AND bf.fileCategory = :fileCategory
        ORDER BY bf.createdAt DESC
    """)
    Optional<BoardPointFilesEntity> findByIdAndBoardPointIdAndPrefectureAndFileCategory(@Param(value = "fileId") UUID fileId, @Param(value = "boardPointId") UUID boardPointId, @Param(value = "prefectureId") UUID prefectureId, @Param(value = "fileCategory") FileCategory fileCategory);

    @Query("""
        SELECT bf FROM BoardPointFilesEntity bf
        INNER JOIN bf.boardPoint b
        INNER JOIN bf.prefecture p
        WHERE b.id = :boardPointId
        AND p.id = :prefectureId
        AND bf.fileCategory = :fileCategory 
    """)
    List<BoardPointFilesEntity> findAllByBoardPointIdAndPrefectureIdAndFileCategory(@Param(value = "boardPointId") UUID boardPointId, @Param(value = "prefectureId") UUID prefectureId, @Param(value = "fileCategory") FileCategory fileCategory);

    @Query("""
        SELECT bf FROM BoardPointFilesEntity bf
        INNER JOIN bf.boardPoint b
        INNER JOIN bf.prefecture p
        WHERE b.id = :boardPointId
        AND p.id = :prefectureId
        AND bf.fileCategory = :fileCategory
    """)
    Optional<BoardPointFilesEntity> findByBoardPointIdAndPrefectureIdAndFileCategory(@Param(value = "boardPointId") UUID boardPointId, @Param(value = "prefectureId") UUID prefectureId, @Param(value = "fileCategory") FileCategory fileCategory);
}
