package com.rota.facil.journey.persistence.repositories;

import com.rota.facil.journey.persistence.entities.TripUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TripUserRepository extends JpaRepository<TripUserEntity, UUID> {
    boolean existsByTripId(UUID tripId);

    @Query(value = """
        SELECT tu.* FROM trips_users_tb tu
        INNER JOIN trips_tb t USING(trip_id)
        INNER JOIN user_tb u USING(user_id)
        WHERE t.trip_id = :tripId
        AND u.id = :userId
        AND ST_DWithin(t.geom, ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326)::geography, 30)
    """, nativeQuery = true)
    Optional<TripUserEntity> findTripNotStartedByLatitudeAndLongitude(@Param("tripId") UUID tripId, @Param("latitude") Double latitude, @Param("longitude") Double longitude, @Param("userId") UUID userId);

    @Query("""
        SELECT COUNT(u) FROM TripUserEntity tu
        INNER JOIN tu.student u
        WHERE tu.going = :going
        AND tu.return_ = :return_
    """)
    Long countStudentsToGoTrip(@Param("going") boolean going, @Param("return_") boolean return_);
}
