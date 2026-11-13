package com.rota.facil.files.business.helpers;

import com.rota.facil.files.domain.FileCategory;
import com.rota.facil.files.domain.OwnerType;
import com.rota.facil.files.http.exceptions.FileNotFoundException;
import com.rota.facil.files.persistence.entities.FileEntity;
import com.rota.facil.files.persistence.repositories.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FindBoardPointPhotoByIdHelper {
    private final FileRepository fileRepository;

    public FileEntity execute(UUID photoId, UUID boardPointId, UUID prefectureId) {
        return fileRepository.findByIdAndOwnerIdAndPrefectureIdAndFileCategory(
                        photoId, boardPointId, prefectureId, FileCategory.PHOTO
                )
                .filter(file -> file.getOwnerType() == OwnerType.BOARD_POINT)
                .orElseThrow(() -> new FileNotFoundException("Foto do ponto de embarque não foi encontrada"));
    }
}
