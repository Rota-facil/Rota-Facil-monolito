package com.rota.facil.files.business.helpers;

import com.rota.facil.files.domain.FileCategory;
import com.rota.facil.files.exceptions.FileNotFoundException;
import com.rota.facil.files.persistence.entities.FileEntity;
import com.rota.facil.files.persistence.repositories.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FindVehiclePhotoByIdHelper {
    private final FileRepository fileRepository;

    public FileEntity execute(UUID photoId, UUID vehicleId, UUID prefectureId) {
        return fileRepository.findByIdAndOwnerIdAndPrefectureIdAndFileCategory(
                        photoId,
                        vehicleId,
                        prefectureId,
                        FileCategory.VEHICLE_PHOTO
                )
                .orElseThrow(() -> new FileNotFoundException("Foto do veículo não foi encontrada"));
    }
}
