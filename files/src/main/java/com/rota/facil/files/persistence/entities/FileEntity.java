package com.rota.facil.files.persistence.entities;

import com.rota.facil.files.domain.FileCategory;
import com.rota.facil.files.domain.OwnerType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "files_tb")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FileEntity {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "file_id")
    private UUID id;
    @Column(name = "original_filename", nullable = false)
    private String originalFilename;
    @Column(name = "object_key", nullable = false, unique = true)
    private String objectKey;
    @Column(name = "owner_id", nullable = false)
    private UUID ownerId;
    @Column(name = "creator_id", nullable = false)
    private UUID creatorId;
    @Column(name = "prefecture_id", nullable = false)
    private UUID prefectureId;
    @Enumerated(EnumType.STRING)
    @Column(name = "owner_type", nullable = false)
    private OwnerType ownerType;
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

    public static FileEntity newVehiclePhoto(UUID vehicleId, UUID creatorId, UUID prefectureId,
                                             String originalFilename, String mimeType, long fileSizeBytes) {
        return FileEntity.builder()
                .originalFilename(originalFilename)
                .objectKey("vehicles/" + vehicleId + "/photos/" + UUID.randomUUID())
                .ownerId(vehicleId)
                .creatorId(creatorId)
                .prefectureId(prefectureId)
                .ownerType(OwnerType.VEHICLE)
                .fileCategory(FileCategory.VEHICLE_PHOTO)
                .mimeType(mimeType)
                .fileSizeBytes(fileSizeBytes)
                .build();
    }

    public void update(String originalFilename, String mimeType, long fileSizeBytes) {
        this.originalFilename = originalFilename;
        this.mimeType = mimeType;
        this.fileSizeBytes = fileSizeBytes;
    }
}
