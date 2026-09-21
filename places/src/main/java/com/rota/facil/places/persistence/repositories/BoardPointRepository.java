package com.rota.facil.places.persistence.repositories;

import com.rota.facil.places.persistence.entitites.BoardPointEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BoardPointRepository extends JpaRepository<BoardPointEntity, UUID> {

    @Modifying(clearAutomatically = true)
    @Query("UPDATE BoardPointEntity e SET e.active = false WHERE e.prefectureId = :prefectureId")
    void deactivateAllByPrefectureId(@Param("prefectureId") UUID prefectureId);
}
