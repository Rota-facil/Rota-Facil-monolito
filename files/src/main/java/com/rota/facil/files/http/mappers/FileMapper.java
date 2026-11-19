package com.rota.facil.files.http.mappers;

import com.rota.facil.files.http.dto.response.FileWithValidationResponse;
import com.rota.facil.files.http.dto.response.FileResponse;
import com.rota.facil.files.http.dto.response.users.FileUsersResponse;
import com.rota.facil.files.persistence.entities.FileWithValidationEntity;
import com.rota.facil.files.persistence.entities.FileEntity;
import com.rota.facil.files.storage.FileStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FileMapper {
    private final FileStorage fileStorage;

    public FileResponse map(FileEntity entity) {
        return new FileResponse(
                entity.getId(),
                entity.getOriginalFilename(),
                this.fileStorage.createTemporaryUrl(entity.getObjectKey()),
                entity.getFileCategory(),
                entity.getCreatedAt()
        );
    }

    public FileUsersResponse mapWithUsers(FileEntity entity) {
        return new FileUsersResponse(
                entity.getId(),
                entity.getCreator().getId(),
                entity.getOriginalFilename(),
                this.fileStorage.createTemporaryUrl(entity.getObjectKey()),
                entity.getFileCategory(),
                entity.getCreatedAt()
        );
    }

    public FileWithValidationResponse map(FileWithValidationEntity entity) {
        return new FileWithValidationResponse(
                entity.getId(),
                entity.getOriginalFilename(),
                this.fileStorage.createTemporaryUrl(entity.getObjectKey()),
                entity.getFileCategory(),
                entity.getValidationStatus(),
                entity.getCreatedAt()
        );
    }
}
