package com.rota.facil.places.persistence.repositories;

import com.rota.facil.places.persistence.entitites.InstitutionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InstitutionRepository extends JpaRepository<InstitutionEntity, UUID> {

    @Modifying(clearAutomatically = true)
    @Query("UPDATE InstitutionEntity e SET e.active = false WHERE e.prefectureId = :prefectureId")
    void deactivateAllByPrefectureId(@Param("prefectureId") UUID prefectureId);
}
