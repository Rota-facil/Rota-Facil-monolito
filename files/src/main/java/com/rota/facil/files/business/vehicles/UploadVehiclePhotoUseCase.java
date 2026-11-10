package com.rota.facil.files.business.vehicles;

import com.rota.facil.files.business.helpers.ValidateImageHelper;
import com.rota.facil.files.domain.FileCategory;
import com.rota.facil.files.domain.OwnerType;
import com.rota.facil.files.http.dto.response.FileResponse;
import com.rota.facil.files.http.mappers.FileMapper;
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
    private final FileMapper fileMapper;

    @Transactional
    public FileResponse execute(UUID vehicleId, MultipartFile file, UserEntity currentUser) {
        this.findVehicleByIdHelper.execute(vehicleId, currentUser.getPrefecture().getId());
        this.validateImageHelper.execute(file);

        FileEntity saved = this.fileRepository.save(FileEntity.newFile(
                vehicleId, currentUser.getId(), currentUser.getPrefecture().getId(),
                file.getOriginalFilename(), file.getContentType(), file.getSize(),
                OwnerType.VEHICLE, FileCategory.PHOTO
        ));

        this.fileStorage.upload(file, saved.getObjectKey());

        return this.fileMapper.map(saved);
    }
}
