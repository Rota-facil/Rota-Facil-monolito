package com.rota.facil.vehicles.persistence.repositories;

import com.rota.facil.vehicles.entities.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VehicleRepository extends JpaRepository<VehicleEntity, UUID> {
    Optional<VehicleEntity> findByIdAndPrefectureId(UUID id, UUID prefectureId);
    Optional<VehicleEntity> findByIdAndPrefectureIdAndActiveTrue(UUID id, UUID prefectureId);
    Optional<VehicleEntity> findByDriverIdAndActiveTrue(UUID driverId);
    List<VehicleEntity> findAllByPrefectureIdAndActiveTrue(UUID prefectureId);

    @Modifying(clearAutomatically = true)
    @Query("""
        UPDATE VehicleEntity v
        SET v.active = false,
            v.status = com.rota.facil.vehicles.domain.VehicleStatus.OUT_OF_OPERATION,
            v.driver = null
        WHERE v.prefectureId = :prefectureId
    """)
    void deactivateAllByPrefectureId(@Param("prefectureId") UUID prefectureId);
}
