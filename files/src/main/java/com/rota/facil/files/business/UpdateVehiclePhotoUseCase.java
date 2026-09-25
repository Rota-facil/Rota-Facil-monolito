package com.rota.facil.files.business;

import com.rota.facil.files.business.helpers.FindVehiclePhotoByIdHelper;
import com.rota.facil.files.business.helpers.ValidateImageHelper;
import com.rota.facil.files.http.dto.response.FileResponse;
import com.rota.facil.files.http.mappers.FileResponseMapper;
import com.rota.facil.files.persistence.entities.FileEntity;
import com.rota.facil.files.persistence.repositories.FileRepository;
import com.rota.facil.files.storage.FileStorage;
import com.rota.facil.users.persistence.entities.UserEntity;
import com.rota.facil.vehicles.business.helpers.FindVehicleByIdHelper;
import lombok.RequiredArgsConstructor;
import com.rota.facil.annotations.UseCase;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class UpdateVehiclePhotoUseCase {
    private final FindVehicleByIdHelper findVehicleByIdHelper;
    private final FindVehiclePhotoByIdHelper findVehiclePhotoByIdHelper;
    private final ValidateImageHelper validateImageHelper;
    private final FileRepository fileRepository;
    private final FileStorage fileStorage;
    private final FileResponseMapper fileResponseMapper;

    @Transactional
    public FileResponse execute(
            UUID vehicleId,
            UUID photoId,
            MultipartFile newPhoto,
            UserEntity currentUser
    ) {
        UUID prefectureId = currentUser.getPrefectureId();
        findVehicleByIdHelper.execute(vehicleId, prefectureId);
        validateImageHelper.execute(newPhoto);
        FileEntity photo = findVehiclePhotoByIdHelper.execute(photoId, vehicleId, prefectureId);

        fileStorage.upload(newPhoto, photo.getObjectKey());
        photo.update(newPhoto.getOriginalFilename(), newPhoto.getContentType(), newPhoto.getSize());
        FileEntity updated = fileRepository.save(photo);
        return fileResponseMapper.map(updated);
    }
}
