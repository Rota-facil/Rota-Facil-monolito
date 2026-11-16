package com.rota.facil.files.persistence.entities;

import com.rota.facil.files.domain.enums.FileCategory;
import com.rota.facil.files.domain.enums.FileValidationStatus;
import com.rota.facil.prefectures.persistence.entitites.PrefectureEntity;
import com.rota.facil.users.persistence.entities.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Table(name = "files_with_validation_tb")
@SuperBuilder
@Entity
@PrimaryKeyJoinColumn(name = "file_id")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileWithValidationEntity extends FileEntity {
    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "validation_status")
    private FileValidationStatus validationStatus = FileValidationStatus.ANALYSIS;

    public boolean isApproved() {
        return this.validationStatus.equals(FileValidationStatus.APPROVED);
    }

    public boolean isAnalysis() {
        return this.validationStatus.equals(FileValidationStatus.ANALYSIS);
    }

    public boolean isReject() {
        return this.validationStatus.equals(FileValidationStatus.REJECTED);
    }

    public void update(String originalFilename, String mimeType, long fileSizeBytes) {
        super.setOriginalFilename(originalFilename);
        super.setMimeType(mimeType);
        super.setFileSizeBytes(fileSizeBytes);
        this.validationStatus = FileValidationStatus.ANALYSIS;

    }

    public static FileWithValidationEntity newFile(
            UserEntity creator, PrefectureEntity prefecture,
            String originalFilename, String mimeType, long fileSizeBytes,
            FileCategory fileCategory
    ) {
        FileWithValidationEntity fileWithValidationCreated = FileWithValidationEntity.builder()
                .originalFilename(originalFilename)
                .creator(creator)
                .prefecture(prefecture)
                .fileCategory(fileCategory)
                .mimeType(mimeType)
                .fileSizeBytes(fileSizeBytes)
                .validationStatus(FileValidationStatus.ANALYSIS)
                .build();
        fileWithValidationCreated.generateObjectKey();
        return fileWithValidationCreated;
    }
}
