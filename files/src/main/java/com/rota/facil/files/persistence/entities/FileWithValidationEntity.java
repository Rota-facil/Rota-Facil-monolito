package com.rota.facil.files.persistence.entities;

import com.rota.facil.files.domain.FileCategory;
import com.rota.facil.files.domain.OwnerType;
import com.rota.facil.prefectures.persistence.entitites.PrefectureEntity;
import com.rota.facil.users.persistence.entities.UserEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Table(name = "files_with_validation_tb")
@SuperBuilder
@Entity
@PrimaryKeyJoinColumn(name = "file_id")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileWithValidationEntity extends FileEntity {
    @Builder.Default
    private boolean isValid = false;

    public static FileWithValidationEntity newFile(
            UUID ownerId, UserEntity creator, PrefectureEntity prefecture,
            String originalFilename, String mimeType, long fileSizeBytes,
            OwnerType ownerType, FileCategory fileCategory
    ) {
        return FileWithValidationEntity.builder()
                .originalFilename(originalFilename)
                .objectKey(generateObjectKey(originalFilename, ownerId, ownerType, fileCategory))
                .creator(creator)
                .prefecture(prefecture)
                .fileCategory(fileCategory)
                .mimeType(mimeType)
                .fileSizeBytes(fileSizeBytes)
                .isValid(false)
                .build();
    }
}
