package com.rota.facil.files.http.mappers;

import com.rota.facil.files.http.dto.response.FileResponse;
import com.rota.facil.files.persistence.entities.FileEntity;
import com.rota.facil.files.storage.FileStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FileResponseMapper {
    private final FileStorage fileStorage;

    public FileResponse map(FileEntity file) {
        return new FileResponse(
                file.getId(),
                file.getOriginalFilename(),
                fileStorage.createTemporaryUrl(file.getObjectKey()),
                file.getFileCategory(),
                file.getOwnerType(),
                file.getCreatedAt()
        );
    }
}
