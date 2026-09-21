package com.rota.facil.files.business;

import com.rota.facil.files.business.helpers.FindVehiclePhotoByIdHelper;
import com.rota.facil.files.http.dto.response.FileResponse;
import com.rota.facil.files.http.mappers.FileResponseMapper;
import com.rota.facil.users.persistence.entities.UserEntity;
import com.rota.facil.vehicles.business.helpers.FindVehicleByIdHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FetchVehiclePhotoUseCase {
    private final FindVehicleByIdHelper findVehicleByIdHelper;
    private final FindVehiclePhotoByIdHelper findVehiclePhotoByIdHelper;
    private final FileResponseMapper fileResponseMapper;

    @Transactional(readOnly = true)
    public FileResponse execute(UUID vehicleId, UUID photoId, UserEntity currentUser) {
        UUID prefectureId = currentUser.getPrefectureId();
        findVehicleByIdHelper.execute(vehicleId, prefectureId);
        return fileResponseMapper.map(
                findVehiclePhotoByIdHelper.execute(photoId, vehicleId, prefectureId)
        );
    }
}
