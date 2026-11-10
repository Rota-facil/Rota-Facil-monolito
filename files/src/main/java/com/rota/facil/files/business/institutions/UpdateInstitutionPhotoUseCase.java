package com.rota.facil.files.business.institutions;

import com.rota.facil.annotations.UseCase;
import com.rota.facil.files.business.helpers.FindInstitutionPhotoByIdHelper;
import com.rota.facil.files.business.helpers.ValidateImageHelper;
import com.rota.facil.files.http.dto.response.FileResponse;
import com.rota.facil.files.http.mappers.FileMapper;
import com.rota.facil.files.persistence.entities.FileEntity;
import com.rota.facil.files.persistence.repositories.FileRepository;
import com.rota.facil.files.storage.FileStorage;
import com.rota.facil.places.business.helpers.institutions.FindInstitutionByIdHelper;
import com.rota.facil.users.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class UpdateInstitutionPhotoUseCase {
    private final FindInstitutionByIdHelper findInstitutionByIdHelper;
    private final FindInstitutionPhotoByIdHelper findInstitutionPhotoByIdHelper;
    private final ValidateImageHelper validateImageHelper;
    private final FileRepository fileRepository;
    private final FileStorage fileStorage;
    private final FileMapper fileMapper;

    @Transactional
    public FileResponse execute(UUID institutionId, UUID photoId, MultipartFile newPhoto, UserEntity currentUser) {
        UUID prefectureId = currentUser.getPrefecture().getId();
        findInstitutionByIdHelper.execute(institutionId, prefectureId);
        validateImageHelper.execute(newPhoto);
        FileEntity photo = findInstitutionPhotoByIdHelper.execute(photoId, institutionId, prefectureId);
        fileStorage.upload(newPhoto, photo.getObjectKey());
        photo.update(newPhoto.getOriginalFilename(), newPhoto.getContentType(), newPhoto.getSize());
        return fileMapper.map(fileRepository.save(photo));
    }
}
