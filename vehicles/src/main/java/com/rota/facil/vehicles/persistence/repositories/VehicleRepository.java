package com.rota.facil.vehicles.persistence.repositories;

import com.rota.facil.prefectures.persistence.entitites.PrefectureEntity;
import com.rota.facil.vehicles.persistence.entities.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VehicleRepository extends JpaRepository<VehicleEntity, UUID> {
    long countByPrefectureIdAndActiveTrue(UUID prefectureId);
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

    @Transactional
    @Modifying
    @Query("""
        UPDATE VehicleEntity v SET v.driver = NULL
        WHERE v.driver.id = :driverId
    """)
    void removeDriverFromVehicle(@Param("driverId") UUID driverId);

    @Query("""
        SELECT v FROM VehicleEntity v
        INNER JOIN v.driver d
        WHERE d.id = :driverId
        AND v.prefectureId = :prefectureId
        AND v.active IS TRUE
        AND d.active IS TRUE
    """)
    Optional<VehicleEntity> findByDriverIdAndPrefectureId(@Param(value = "driverId") UUID driverId, @Param(value = "prefectureId") UUID prefectureId);

    @Query("""
        SELECT v FROM VehicleEntity v
        
    """)
    Optional<VehicleEntity> findVehicleInOutOfOperationByDriverId(@Param(value = "driverId") UUID driverId);

    @Query("""
        SELECT v FROM VehicleEntity v
        WHERE v.id IN :vehiclesId
        AND v.active IS TRUE
        AND v.prefectureId = :prefectureId
    """)
    List<VehicleEntity> findAllVehiclesByVehiclesIdAndPrefectureId(@Param(value = "vehiclesId") List<UUID> vehiclesId, @Param(value = "prefectureId") UUID prefectureId);
}
