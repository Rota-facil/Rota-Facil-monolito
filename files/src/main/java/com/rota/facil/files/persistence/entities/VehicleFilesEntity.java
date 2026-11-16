package com.rota.facil.files.persistence.entities;

import com.rota.facil.files.domain.enums.FileCategory;
import com.rota.facil.prefectures.persistence.entitites.PrefectureEntity;
import com.rota.facil.users.persistence.entities.UserEntity;
import com.rota.facil.vehicles.persistence.entities.VehicleEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@SuperBuilder
@Table(name = "vehicles_files_tb")
@Entity
@Getter @Setter
@PrimaryKeyJoinColumn(name = "file_id")
@AllArgsConstructor
@NoArgsConstructor
public class VehicleFilesEntity extends FileEntity {
    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private VehicleEntity vehicle;

    public static VehicleFilesEntity newFile(
            UserEntity creator, PrefectureEntity prefecture,
            String originalFilename, String mimeType, long fileSizeBytes,
            FileCategory fileCategory, VehicleEntity vehicle
    ) {
        VehicleFilesEntity vehicleFileCreated = VehicleFilesEntity.builder()
                .originalFilename(originalFilename)
                .creator(creator)
                .vehicle(vehicle)
                .prefecture(prefecture)
                .fileCategory(fileCategory)
                .mimeType(mimeType)
                .fileSizeBytes(fileSizeBytes)
                .build();
        vehicleFileCreated.generateObjectKey();
        return vehicleFileCreated;
    }

    public void generateObjectKey() {
        super.setObjectKey(super.getFileCategory().name().toLowerCase() + "/" + super.getPrefecture().getName() + "_" + super.getPrefecture().getId() + "/" + this.vehicle.getPlate() + "_" + this.vehicle.getId() + "/" + super.getOriginalFilename() + "_" + UUID.randomUUID());
    }
}
