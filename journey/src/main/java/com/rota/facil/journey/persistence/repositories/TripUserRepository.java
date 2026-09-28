package com.rota.facil.journey.persistence.repositories;

import com.rota.facil.journey.persistence.entities.TripEntity;
import com.rota.facil.journey.persistence.entities.TripUserEntity;
import com.rota.facil.places.persistence.entitites.BoardPointEntity;
import com.rota.facil.places.persistence.entitites.InstitutionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
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

    @Query("""
        SELECT DISTINCT b FROM TripUserEntity tu
        INNER JOIN tu.trip t
        INNER JOIN t.route r
        INNER JOIN r.boardPoints b
        WHERE t.id = :tripId
        AND tu.going IS TRUE
    """)
    List<BoardPointEntity> findAllBoardPointByTripIdOfStudentsGoing(@Param("tripId") UUID tripId);

    @Query("""
        SELECT DISTINCT b FROM TripUserEntity tu
        INNER JOIN tu.trip t
        INNER JOIN t.route r
        INNER JOIN r.boardPoints b
        WHERE t.id = :tripId
        AND tu.return_ IS TRUE
    """)
    List<BoardPointEntity> findAllBoardPointByTripIdOfStudentsReturn(@Param("tripId") UUID tripId);

    @Query("""
        SELECT DISTINCT i FROM TripUserEntity tu
        INNER JOIN tu.trip t
        INNER JOIN t.route r
        INNER JOIN r.institutions i
        WHERE t.id = :tripId
        AND tu.going IS TRUE
    """)
    List<InstitutionEntity> findAllInstitutionsByTripIdOfStudentsGoing(@Param("tripId") UUID tripId);

    @Query("""
        SELECT DISTINCT i FROM TripUserEntity tu
        INNER JOIN tu.trip t
        INNER JOIN t.route r
        INNER JOIN r.institutions i
        WHERE t.id = :tripId
        AND tu.return_ IS TRUE
    """)
    List<InstitutionEntity> findAllInstitutionsByTripIdOfStudentsReturn(@Param("tripId") UUID tripId);

    @Modifying
    @Query("""
        UPDATE TripUserEntity tu
        SET tu.presence = Presence.ABSENT
        WHERE tu.presence = Presence.PENDING
        AND tu.trip.id = :tripId
    """)
    void setAbsentsGoingStudentsByTripId(@Param("tripId") UUID tripId);

    @Query("""
        SELECT t FROM TripUserEntity tu
        INNER JOIN tu.trip t
        INNER JOIN tu.student s
        WHERE s.id = :studentId
        AND t.prefectureId = :prefectureId
        AND t.createdAt = CURRENT TIMESTAMP
           
    """)
    List<TripEntity> findAllTripsByStudentIdAndPrefectureId(UUID studentId, UUID prefectureId);
}
