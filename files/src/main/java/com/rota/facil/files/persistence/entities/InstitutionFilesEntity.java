package com.rota.facil.files.persistence.entities;

import com.rota.facil.files.domain.enums.FileCategory;
import com.rota.facil.places.persistence.entitites.InstitutionEntity;
import com.rota.facil.prefectures.persistence.entitites.PrefectureEntity;
import com.rota.facil.users.persistence.entities.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@SuperBuilder
@Table(name = "institutions_files_tb")
@Entity
@Getter @Setter
@PrimaryKeyJoinColumn(name = "file_id")
@AllArgsConstructor
@NoArgsConstructor
public class InstitutionFilesEntity extends FileEntity{
    @ManyToOne
    @JoinColumn(name = "institution_id")
    private InstitutionEntity institution;

    public static InstitutionFilesEntity newFile(
            UserEntity creator, PrefectureEntity prefecture,
            String originalFilename, String mimeType, long fileSizeBytes,
            FileCategory fileCategory, InstitutionEntity boardPoint
    ) {
        InstitutionFilesEntity institutionFileCreated = InstitutionFilesEntity.builder()
                .originalFilename(originalFilename)
                .creator(creator)
                .institution(boardPoint)
                .prefecture(prefecture)
                .fileCategory(fileCategory)
                .mimeType(mimeType)
                .fileSizeBytes(fileSizeBytes)
                .build();
        institutionFileCreated.generateObjectKey();
        return institutionFileCreated;
    }

    public void generateObjectKey() {
        super.setObjectKey(super.getFileCategory().name().toLowerCase() + "/" + super.getPrefecture().getName() + "_" + super.getPrefecture().getId() + "/" + this.institution.getName() + "_" + this.institution.getId() + "/" + super.getOriginalFilename() + "_" + UUID.randomUUID());
    }
}
