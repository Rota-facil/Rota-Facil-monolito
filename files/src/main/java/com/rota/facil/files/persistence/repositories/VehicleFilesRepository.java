package com.rota.facil.files.persistence.repositories;

import com.rota.facil.files.domain.FileCategory;
import com.rota.facil.files.persistence.entities.VehicleFilesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VehicleFilesRepository extends JpaRepository<VehicleFilesEntity, UUID> {
    @Query("""
        SELECT vf FROM VehicleFilesEntity vf
        INNER JOIN vf.vehicle v
        INNER JOIN vf.prefecture p
        WHERE vf.id = :fileId
        AND v.id = :vehicleId
        AND p.id = :prefectureId
        AND vf.fileCategory = :fileCategory
    """)
    Optional<VehicleFilesEntity> findByIdAndVehicleIdAndPrefectureAndFileCategory(@Param(value = "fileId") UUID fileId, @Param(value = "vehicleId") UUID vehicleId, @Param(value = "prefectureId") UUID prefectureId, @Param(value = "fileCategory") FileCategory fileCategory);

    @Query("""
        SELECT vf FROM VehicleFilesEntity vf
        INNER JOIN vf.vehicle v
        INNER JOIN vf.prefecture p
        WHERE v.id = :vehicleId
        AND p.id = :prefectureId
        AND vf.fileCategory = :fileCategory
    """)
    List<VehicleFilesEntity> findAllByVehicleIdAndPrefectureIdAndFileCategory(@Param(value = "vehicleId") UUID vehicleId, @Param(value = "prefectureId") UUID prefectureId, @Param(value = "fileCategory") FileCategory fileCategory);
}
