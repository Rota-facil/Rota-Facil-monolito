package com.rota.facil.places.persistence.repositories;

import com.rota.facil.places.persistence.entitites.InstitutionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface InstitutionRepository extends JpaRepository<InstitutionEntity, UUID> {
    Optional<InstitutionEntity> findByIdAndPrefectureIdAndActiveTrue(UUID id, UUID prefectureId);
    Page<InstitutionEntity> findAllByPrefectureIdAndActiveTrue(UUID prefectureId, Pageable pageable);


    @Modifying(clearAutomatically = true)
    @Query("UPDATE InstitutionEntity e SET e.active = false WHERE e.prefectureId = :prefectureId")
    void deactivateAllByPrefectureId(@Param("prefectureId") UUID prefectureId);

    @Query("""
        SELECT i FROM InstitutionEntity i
        WHERE i.id = :institutionId
        AND i.prefectureId = :prefectureId
        AND i.active IS TRUE
    """)
    List<InstitutionEntity> findAllByIdAndPrefectureIdAndActive(@Param("institutionsId") List<UUID> institutionId, @Param("prefectureId") UUID prefectureId);
}
