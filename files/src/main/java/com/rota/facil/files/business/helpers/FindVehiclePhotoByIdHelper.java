package com.rota.facil.files.business.helpers;

import com.rota.facil.files.domain.enums.FileCategory;
import com.rota.facil.files.http.exceptions.FileNotFoundException;
import com.rota.facil.files.persistence.entities.FileEntity;
import com.rota.facil.files.persistence.repositories.VehicleFilesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FindVehiclePhotoByIdHelper {
    private final VehicleFilesRepository vehicleFilesRepository;

    public FileEntity execute(UUID photoId, UUID vehicleId, UUID prefectureId) {
        return this.vehicleFilesRepository.findByIdAndVehicleIdAndPrefectureAndFileCategory(
                        photoId,
                        vehicleId,
                        prefectureId,
                        FileCategory.VEHICLE_PHOTO
                )
                .orElseThrow(() -> new FileNotFoundException("Foto do veículo não foi encontrada"));
    }
}
