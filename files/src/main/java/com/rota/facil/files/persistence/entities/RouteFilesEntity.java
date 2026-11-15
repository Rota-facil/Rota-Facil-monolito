package com.rota.facil.files.persistence.entities;

import com.rota.facil.files.domain.FileCategory;
import com.rota.facil.files.domain.OwnerType;
import com.rota.facil.journey.persistence.entities.RouteEntity;
import com.rota.facil.prefectures.persistence.entitites.PrefectureEntity;
import com.rota.facil.users.persistence.entities.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Table(name = "routes_files_tb")
@SuperBuilder
@Entity
@PrimaryKeyJoinColumn(name = "file_id")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RouteFilesEntity extends FileEntity {
    @ManyToOne
    @JoinColumn(name = "route_id")
    private RouteEntity route;

    public static RouteFilesEntity newFile(
            UUID ownerId, UserEntity creator, PrefectureEntity prefecture,
            String originalFilename, String mimeType, long fileSizeBytes,
            OwnerType ownerType, FileCategory fileCategory, RouteEntity routeEntity
    ) {
        return RouteFilesEntity.builder()
                .originalFilename(originalFilename)
                .objectKey(generateObjectKey(originalFilename, ownerId, ownerType, fileCategory))
                .creator(creator)
                .route(routeEntity)
                .prefecture(prefecture)
                .fileCategory(fileCategory)
                .mimeType(mimeType)
                .fileSizeBytes(fileSizeBytes)
                .build();
    }
}
