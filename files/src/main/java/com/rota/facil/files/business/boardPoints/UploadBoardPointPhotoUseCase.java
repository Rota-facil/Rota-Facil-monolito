package com.rota.facil.files.business.boardPoints;

import com.rota.facil.annotations.UseCase;
import com.rota.facil.files.business.helpers.ValidateImageHelper;
import com.rota.facil.files.domain.FileCategory;
import com.rota.facil.files.domain.OwnerType;
import com.rota.facil.files.http.dto.response.FileResponse;
import com.rota.facil.files.http.mappers.FileMapper;
import com.rota.facil.files.persistence.entities.FileEntity;
import com.rota.facil.files.persistence.repositories.FileRepository;
import com.rota.facil.files.storage.FileStorage;
import com.rota.facil.places.business.helpers.boardpoints.FindBoardPointByIdHelper;
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
    private final FileRepository fileRepository;
    private final FileStorage fileStorage;
    private final FileMapper fileMapper;

    @Transactional
    public FileResponse execute(UUID boardPointId, MultipartFile file, UserEntity currentUser) {
        UUID prefectureId = currentUser.getPrefecture().getId();
        findBoardPointByIdHelper.execute(boardPointId, prefectureId);
        validateImageHelper.execute(file);
        FileEntity saved = fileRepository.save(FileEntity.newFile(
                boardPointId, currentUser.getId(), prefectureId,
                file.getOriginalFilename(), file.getContentType(), file.getSize(),
                OwnerType.BOARD_POINT, FileCategory.PHOTO
        ));
        fileStorage.upload(file, saved.getObjectKey());
        return fileMapper.map(saved);
    }
}
