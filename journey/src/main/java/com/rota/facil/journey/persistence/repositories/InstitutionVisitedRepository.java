package com.rota.facil.journey.persistence.repositories;

import com.rota.facil.journey.persistence.entities.InstitutionVisitedEntity;
import com.rota.facil.places.persistence.entitites.InstitutionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InstitutionVisitedRepository extends JpaRepository<InstitutionVisitedEntity, UUID> {
    @Query("""
        SELECT iv FROM InstitutionVisitedEntity iv
        INNER JOIN iv.institution i
        INNER JOIN iv.trip t
        WHERE i.id = :institutionId
        AND t.id = :tripId
    """)
    Optional<InstitutionVisitedEntity> findByInstitutionIdAndTripId(@Param("institutionId") UUID institutionId, @Param("tripId") UUID tripId);

    @Query("""
        SELECT i FROM InstitutionVisitedEntity iv
        INNER JOIN iv.institution i
        INNER JOIN iv.trip t
        WHERE t.id = :tripId
    """)
    List<InstitutionEntity> findAllInstitutionsByTripId(@Param("tripId") UUID tripId);

    @Query("""
        SELECT iv FROM InstitutionVisitedEntity iv
        WHERE iv.trip.id = :tripId
        AND iv.return_ = true
    """)
    List<InstitutionVisitedEntity> findAllReturnedByTripId(@Param("tripId") UUID tripId);
}
