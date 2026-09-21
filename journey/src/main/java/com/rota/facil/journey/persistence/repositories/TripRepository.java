package com.rota.facil.journey.persistence.repositories;

import com.rota.facil.journey.persistence.entities.TripEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TripRepository extends JpaRepository<TripEntity, UUID> {

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
}
