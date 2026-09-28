package com.rota.facil.journey.persistence.repositories;

import com.rota.facil.journey.persistence.entities.BoardPointVisitedEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BoardPointVisitedRepository extends JpaRepository<BoardPointVisitedEntity, UUID> {
    Optional<BoardPointVisitedEntity> findByBoardPointIdAndTripId(UUID boardPointId, UUID tripId);

    @Query("""
        SELECT visited FROM BoardPointVisitedEntity visited
        WHERE visited.trip.id = :tripId
        AND visited.return_ = true
    """)
    List<BoardPointVisitedEntity> findAllReturnedByTripId(@Param("tripId") UUID tripId);
}
