package com.rota.facil.files.persistence.entities;

import com.rota.facil.files.domain.FileCategory;
import com.rota.facil.files.domain.OwnerType;
import com.rota.facil.places.persistence.entitites.BoardPointEntity;
import com.rota.facil.prefectures.persistence.entitites.PrefectureEntity;
import com.rota.facil.users.persistence.entities.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@SuperBuilder
@Table(name = "board_points_files_tb")
@Entity
@Getter @Setter
@PrimaryKeyJoinColumn(name = "file_id")
@AllArgsConstructor
@NoArgsConstructor
public class BoardPointFilesEntity extends FileEntity {
    @ManyToOne
    @JoinColumn(name = "board_point_id")
    private BoardPointEntity boardPoint;

    public static BoardPointFilesEntity newFile(
            UUID ownerId, UserEntity creator, PrefectureEntity prefecture,
            String originalFilename, String mimeType, long fileSizeBytes,
            OwnerType ownerType, FileCategory fileCategory, BoardPointEntity boardPoint
    ) {
        return BoardPointFilesEntity.builder()
                .originalFilename(originalFilename)
                .objectKey(generateObjectKey(originalFilename, ownerId, ownerType, fileCategory))
                .creator(creator)
                .boardPoint(boardPoint)
                .prefecture(prefecture)
                .fileCategory(fileCategory)
                .mimeType(mimeType)
                .fileSizeBytes(fileSizeBytes)
                .build();
    }
}
