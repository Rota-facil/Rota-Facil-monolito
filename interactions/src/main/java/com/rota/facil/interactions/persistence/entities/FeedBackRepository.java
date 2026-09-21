package com.rota.facil.interactions.persistence.entities;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FeedBackRepository extends JpaRepository<FeedBackEntity, UUID> {
    @Query("""
        SELECT AVG(f.note) FROM FeedBackEntity f
        INNER JOIN f.receiver r
        WHERE r.id = :userId
    """)
    Double calculateMediaOfReceiverByUserId(@Param(value = "userId") UUID userId);

    @Query("""
        SELECT f FROM FeedBackEntity f
        INNER JOIN f.receiver r
        WHERE r.id = :userId
        
    """)
    List<FeedBackEntity> findAllByReceiverIdAndPrefectureId(@Param(value = "userId") UUID userId, @Param(value = "userId") UUID prefectureId);
}
