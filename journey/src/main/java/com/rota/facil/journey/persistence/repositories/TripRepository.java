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
}
