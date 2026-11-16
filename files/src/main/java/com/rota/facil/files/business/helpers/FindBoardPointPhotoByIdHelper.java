package com.rota.facil.files.business.helpers;

import com.rota.facil.files.domain.enums.FileCategory;
import com.rota.facil.files.http.exceptions.FileNotFoundException;
import com.rota.facil.files.persistence.entities.BoardPointFilesEntity;
import com.rota.facil.files.persistence.repositories.BoardPointFilesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FindBoardPointPhotoByIdHelper {
    private final BoardPointFilesRepository boardPointFilesRepository;

    public BoardPointFilesEntity execute(UUID photoId, UUID boardPointId, UUID prefectureId) {
        return this.boardPointFilesRepository.findByIdAndBoardPointIdAndPrefectureAndFileCategory(
                        photoId, boardPointId, prefectureId, FileCategory.BOARD_POINT_PHOTO
                )
                .orElseThrow(() -> new FileNotFoundException("Foto do ponto de embarque não foi encontrada"));
    }
}
