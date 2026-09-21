package com.rota.facil.vehicles.persistence.repositories;

import com.rota.facil.vehicles.entities.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VehicleRepository extends JpaRepository<VehicleEntity, UUID> {
    Optional<VehicleEntity> findByIdAndPrefectureId(UUID id, UUID prefectureId);
    Optional<VehicleEntity> findByDriverId(UUID driverId);
}
