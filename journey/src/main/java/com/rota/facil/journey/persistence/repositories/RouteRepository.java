package com.rota.facil.journey.persistence.repositories;

import com.rota.facil.journey.persistence.entities.RouteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RouteRepository extends JpaRepository<RouteEntity, UUID> {

    @Modifying(clearAutomatically = true)
    @Query("UPDATE RouteEntity r SET r.active = false WHERE r.prefectureId = :prefectureId")
    void deactivateAllByPrefectureId(@Param("prefectureId") UUID prefectureId);
}
