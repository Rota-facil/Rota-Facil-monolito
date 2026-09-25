package com.rota.facil.journey.persistence.repositories;

import com.rota.facil.journey.persistence.entities.TripEntity;
import com.rota.facil.places.persistence.entitites.BoardPointEntity;
import com.rota.facil.places.persistence.entitites.InstitutionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TripRepository extends JpaRepository<TripEntity, UUID> {

    @Query(value = """
        SELECT t.* FROM trips_tb t
        WHERE t.trip_id = :tripId
        AND ST_DWithin(t.geom, ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326)::geography, 30)
    """, nativeQuery = true)
    TripEntity findTripByLatitudeAndLongitude(@Param("tripId") UUID tripId, @Param("latitude") Double latitude, @Param("longitude") Double longitude);

    @Modifying(clearAutomatically = true)
    @Query("""
        UPDATE TripEntity t
        SET t.actualStatus = com.rota.facil.journey.domain.Progress.CANCELLED,
            t.reasonOfCancellation = :reason
        WHERE t.prefectureId = :prefectureId
        AND t.actualStatus NOT IN (
            com.rota.facil.journey.domain.Progress.CANCELLED,
            com.rota.facil.journey.domain.Progress.RETURN_FINISHED
        )
    """)
    void cancelAllPendingByPrefectureId(
            @Param("prefectureId") UUID prefectureId,
            @Param("reason") String reason
    );

    @Query("""
        SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END
        FROM TripEntity t
        JOIN t.route r
        JOIN r.boardPoints boardPointRoute
        WHERE boardPointRoute.boardPoint.id = :boardPointId
        AND t.actualStatus IN (
            com.rota.facil.journey.domain.Progress.STARTED,
            com.rota.facil.journey.domain.Progress.STARTED_FINISHED,
            com.rota.facil.journey.domain.Progress.RETURN_STARTED,
            com.rota.facil.journey.domain.Progress.INSTITUTION_ARRIVAL,
            com.rota.facil.journey.domain.Progress.BOARD_POINT_ARRIVAL
        )
    """)
    boolean existsInProgressTripByBoardPointId(@Param("boardPointId") UUID boardPointId);

    @Query("""
        SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END
        FROM TripEntity t
        JOIN t.route r
        JOIN r.institutions institution
        WHERE institution.id = :institutionId
        AND t.actualStatus IN (
            com.rota.facil.journey.domain.Progress.STARTED,
            com.rota.facil.journey.domain.Progress.STARTED_FINISHED,
            com.rota.facil.journey.domain.Progress.RETURN_STARTED,
            com.rota.facil.journey.domain.Progress.INSTITUTION_ARRIVAL,
            com.rota.facil.journey.domain.Progress.BOARD_POINT_ARRIVAL
        )
    """)
    boolean existsInProgressTripByInstitutionId(@Param("institutionId") UUID institutionId);

    @Query("""
        SELECT t FROM TripEntity t
        WHERE t.id = :tripId
        AND t.prefectureId = :prefectureId
        AND t.vehicle.driver.id = :driverId
    """)
    Optional<TripEntity> findTripByIdAndPrefectureIdAndDriverId(
            @Param("tripId") UUID tripId,
            @Param("prefectureId") UUID prefectureId,
            @Param("driverId") UUID driverId
    );

    Optional<TripEntity> findTripByIdAndPrefectureId(UUID tripId, UUID prefectureId);

    @Query("""
        SELECT b FROM TripEntity t
        INNER JOIN t.route r
        INNER JOIN r.boardPoints bs
        INNER JOIN bs.boardPoint b
        WHERE t.id = :tripId
        AND b.id = :boardPointId 
    """)
    Optional<BoardPointEntity> findBoardPointByIdAndBoardPointId(@Param("tripId") UUID tripId, @Param("boardPointId") UUID boardPointId);

    @Query("""
        SELECT i FROM TripEntity t
        INNER JOIN t.route r
        INNER JOIN r.institutions i
        WHERE t.id = :tripId
        AND i.id = :institutionId
    """)
    Optional<InstitutionEntity> findInstitutionByIdAndInstitutionId(@Param("tripId") UUID tripId, @Param("institutionId") UUID institutionId);

    @Query("""
        SELECT b FROM TripEntity t
        INNER JOIN t.route r
        INNER JOIN r.boardPoints b
        WHERE t.id = :tripId
    """)
    List<BoardPointEntity> findAllBoardPointByTripId(@Param("tripId") UUID tripId);
}
