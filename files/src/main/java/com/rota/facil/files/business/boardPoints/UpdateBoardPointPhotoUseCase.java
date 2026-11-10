package com.rota.facil.files.business.boardPoints;

import com.rota.facil.annotations.UseCase;
import com.rota.facil.files.business.helpers.FindBoardPointPhotoByIdHelper;
import com.rota.facil.files.business.helpers.ValidateImageHelper;
import com.rota.facil.files.http.dto.response.FileResponse;
import com.rota.facil.files.http.mappers.FileResponseMapper;
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
public class UpdateBoardPointPhotoUseCase {
    private final FindBoardPointByIdHelper findBoardPointByIdHelper;
    private final FindBoardPointPhotoByIdHelper findBoardPointPhotoByIdHelper;
    private final ValidateImageHelper validateImageHelper;
    private final FileRepository fileRepository;
    private final FileStorage fileStorage;
    private final FileResponseMapper fileResponseMapper;

    @Transactional
    public FileResponse execute(UUID boardPointId, UUID photoId, MultipartFile newPhoto, UserEntity currentUser) {
        UUID prefectureId = currentUser.getPrefecture().getId();
        findBoardPointByIdHelper.execute(boardPointId, prefectureId);
        validateImageHelper.execute(newPhoto);
        FileEntity photo = findBoardPointPhotoByIdHelper.execute(photoId, boardPointId, prefectureId);
        fileStorage.upload(newPhoto, photo.getObjectKey());
        photo.update(newPhoto.getOriginalFilename(), newPhoto.getContentType(), newPhoto.getSize());
        return fileResponseMapper.map(fileRepository.save(photo));
    }
}
