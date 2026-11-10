package com.rota.facil.files.business.vehicles;

import com.rota.facil.files.business.helpers.FindVehiclePhotoByIdHelper;
import com.rota.facil.files.http.dto.response.FileResponse;
import com.rota.facil.files.http.mappers.FileMapper;
import com.rota.facil.users.persistence.entities.UserEntity;
import com.rota.facil.vehicles.business.helpers.FindVehicleByIdHelper;
import lombok.RequiredArgsConstructor;
import com.rota.facil.annotations.UseCase;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class FetchVehiclePhotoUseCase {
    private final FindVehicleByIdHelper findVehicleByIdHelper;
    private final FindVehiclePhotoByIdHelper findVehiclePhotoByIdHelper;
    private final FileMapper fileMapper;

    @Transactional(readOnly = true)
    public FileResponse execute(UUID vehicleId, UUID photoId, UserEntity currentUser) {
        UUID prefectureId = currentUser.getPrefecture().getId();
        findVehicleByIdHelper.execute(vehicleId, prefectureId);
        return fileMapper.map(
                findVehiclePhotoByIdHelper.execute(photoId, vehicleId, prefectureId)
        );
    }
}
