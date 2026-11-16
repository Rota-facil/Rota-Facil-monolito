package com.rota.facil.files.business.useCases.boardPoints;

import com.rota.facil.annotations.UseCase;
import com.rota.facil.files.business.helpers.ValidateImageHelper;
import com.rota.facil.files.domain.enums.FileCategory;
import com.rota.facil.files.http.dto.response.FileResponse;
import com.rota.facil.files.http.mappers.FileMapper;
import com.rota.facil.files.persistence.entities.BoardPointFilesEntity;
import com.rota.facil.files.persistence.repositories.BoardPointFilesRepository;
import com.rota.facil.files.storage.FileStorage;
import com.rota.facil.places.business.helpers.boardpoints.FindBoardPointByIdHelper;
import com.rota.facil.places.persistence.entitites.BoardPointEntity;
import com.rota.facil.users.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class UploadBoardPointPhotoUseCase {
    private final FindBoardPointByIdHelper findBoardPointByIdHelper;
    private final ValidateImageHelper validateImageHelper;
    private final BoardPointFilesRepository boardPointFilesRepository;
    private final FileStorage fileStorage;
    private final FileMapper fileMapper;

    @Transactional
    public FileResponse execute(UUID boardPointId, MultipartFile file, UserEntity currentUser) {
        UUID prefectureId = currentUser.getPrefecture().getId();
        BoardPointEntity boardPointFound = findBoardPointByIdHelper.execute(boardPointId, prefectureId);

        this.validateImageHelper.execute(file);

        BoardPointFilesEntity boardPointFileFound = this.boardPointFilesRepository.findByBoardPointIdAndPrefectureIdAndFileCategory(boardPointId, prefectureId, FileCategory.BOARD_POINT_PHOTO)
                .orElseGet(() ->
                        BoardPointFilesEntity.newFile(
                                currentUser, currentUser.getPrefecture(),
                                file.getOriginalFilename(), file.getContentType(), file.getSize(),
                                FileCategory.BOARD_POINT_PHOTO, boardPointFound

                        )
                );

        if (boardPointFileFound.getId() != null) {
            boardPointFileFound.update(file.getOriginalFilename(), file.getContentType(), file.getSize());
            fileStorage.delete(boardPointFileFound.getObjectKey());

            boardPointFileFound.generateObjectKey();
        }

        BoardPointFilesEntity saved = this.boardPointFilesRepository.save(boardPointFileFound);

        fileStorage.upload(file, saved.getObjectKey());
        
        return fileMapper.map(saved);
    }
}
