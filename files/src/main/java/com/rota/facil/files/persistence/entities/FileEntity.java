package com.rota.facil.files.persistence.entities;

import com.rota.facil.files.domain.enums.FileCategory;
import com.rota.facil.prefectures.persistence.entitites.PrefectureEntity;
import com.rota.facil.users.persistence.entities.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@SuperBuilder
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "files_tb")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileEntity {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "file_id")
    private UUID id;

    @Column(name = "original_filename", nullable = false)
    private String originalFilename;

    @Column(name = "object_key", nullable = false, unique = true)
    private String objectKey;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private UserEntity creator;

    @OneToOne
    @JoinColumn(name = "prefecture_id")
    private PrefectureEntity prefecture;

    @Enumerated(EnumType.STRING)
    @Column(name = "file_category", nullable = false)
    private FileCategory fileCategory;

    @Column(name = "mime_type", nullable = false)
    private String mimeType;

    @Column(name = "file_size_bytes", nullable = false)
    private Long fileSizeBytes;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public static FileEntity newFile(
            UserEntity creator, PrefectureEntity prefecture,
            String originalFilename, String mimeType, long fileSizeBytes,
            FileCategory fileCategory
    ) {
        FileEntity fileCreated = FileEntity.builder()
                .originalFilename(originalFilename)
                .creator(creator)
                .prefecture(prefecture)
                .fileCategory(fileCategory)
                .mimeType(mimeType)
                .fileSizeBytes(fileSizeBytes)
                .build();
        fileCreated.generateObjectKey();
        return fileCreated;
    }

    public void generateObjectKey() {
        switch (fileCategory) {
            case CNH, ENROLLMENT_CERTIFICATE, PROFILE_PIC:  this.objectKey = this.fileCategory.name().toLowerCase() + "/" + this.prefecture.getName() + "_" + this.prefecture.getId() + "/" + this.creator.getEmail() + "_" + this.creator.getId() + "/" + this.originalFilename + "_" + UUID.randomUUID();
        }
    }

    public static String generateObjectKeyForProfilePic(String originalFilename, String prefecture, String userEmail, FileCategory fileCategory) {
        return fileCategory.name().toLowerCase() + "/" + prefecture + "/" + userEmail + "/" + originalFilename + "_" + UUID.randomUUID();
    }

    public void update(String originalFilename, String mimeType, long fileSizeBytes) {
        this.originalFilename = originalFilename;
        this.mimeType = mimeType;
        this.fileSizeBytes = fileSizeBytes;
    }
}
