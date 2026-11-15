package com.rota.facil.files.persistence.entities;

import com.rota.facil.files.domain.FileCategory;
import com.rota.facil.files.domain.OwnerType;
import com.rota.facil.places.persistence.entitites.BoardPointEntity;
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
            UUID ownerId, UserEntity creator, PrefectureEntity prefecture,
            String originalFilename, String mimeType, long fileSizeBytes,
            OwnerType ownerType, FileCategory fileCategory, VehicleEntity vehicle
    ) {
        return VehicleFilesEntity.builder()
                .originalFilename(originalFilename)
                .objectKey(generateObjectKey(originalFilename, ownerId, ownerType, fileCategory))
                .creator(creator)
                .vehicle(vehicle)
                .prefecture(prefecture)
                .fileCategory(fileCategory)
                .mimeType(mimeType)
                .fileSizeBytes(fileSizeBytes)
                .build();
    }
}
