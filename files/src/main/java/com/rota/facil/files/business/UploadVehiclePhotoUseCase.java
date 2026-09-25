package com.rota.facil.files.business;

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
public class UploadVehiclePhotoUseCase {
    private final FindVehicleByIdHelper findVehicleByIdHelper;
    private final ValidateImageHelper validateImageHelper;
    private final FileRepository fileRepository;
    private final FileStorage fileStorage;
    private final FileResponseMapper fileResponseMapper;

    @Transactional
    public FileResponse execute(UUID vehicleId, MultipartFile file, UserEntity currentUser) {
        findVehicleByIdHelper.execute(vehicleId, currentUser.getPrefectureId());
        validateImageHelper.execute(file);

        FileEntity saved = fileRepository.save(FileEntity.newVehiclePhoto(
                vehicleId, currentUser.getId(), currentUser.getPrefectureId(),
                file.getOriginalFilename(), file.getContentType(), file.getSize()));

        fileStorage.upload(file, saved.getObjectKey());

        return fileResponseMapper.map(saved);
    }
}
