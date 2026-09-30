package com.rota.facil.journey.persistence.repositories;

import com.rota.facil.journey.persistence.entities.InstitutionRouteEntity;
import com.rota.facil.journey.persistence.entities.RouteEntity;
import com.rota.facil.places.persistence.entitites.InstitutionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RouteRepository extends JpaRepository<RouteEntity, UUID> {

    @Modifying(clearAutomatically = true)
    @Query("UPDATE RouteEntity r SET r.active = false WHERE r.prefectureId = :prefectureId")
    void deactivateAllByPrefectureId(@Param("prefectureId") UUID prefectureId);

    @Query("""
        SELECT i FROM RouteEntity r
        INNER JOIN r.institutions i
        WHERE r.id = :routeId
        AND i.id = :institutionId
    """)
    Optional<InstitutionRouteEntity> findInstitutionByRouteIdAndInstitutionId(@Param("routeId") UUID routeId, @Param("institutionId") UUID institutionId);

    @Query("""
        SELECT r FROM RouteEntity r
        WHERE r.prefectureId = :prefectureId
    """)
    List<RouteEntity> findAllByPrefectureId(@Param("prefectureId") UUID prefectureId);

    @Query("""
        SELECT r FROM RouteEntity r
        WHERE r.id = :routeId
        AND  r.prefectureId = :prefectureId
    """)
    RouteEntity findByIdAndPrefectureId(@Param("routeId") UUID routeId, @Param("prefectureId") UUID prefectureId);
}
