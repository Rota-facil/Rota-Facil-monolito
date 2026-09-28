package com.rota.facil.journey.persistence.repositories;

import com.rota.facil.journey.domain.Progress;
import com.rota.facil.journey.persistence.entities.TripStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TripStatusRepository extends JpaRepository<TripStatusEntity, UUID> {
    @Query("""
        SELECT COUNT(ts) > 0 FROM TripStatusEntity ts
        INNER JOIN ts.trip t
        WHERE t.id = :tripId
        AND ts.progress IN (:progress)
    """)
    boolean existsByTripIdAndProgress(@Param("tripId") UUID tripId, @Param("progress") Progress progress);
}
