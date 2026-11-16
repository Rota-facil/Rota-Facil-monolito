package com.rota.facil.files.business.useCases.vehicles;

import com.rota.facil.files.domain.enums.FileCategory;
import com.rota.facil.files.http.dto.response.FileResponse;
import com.rota.facil.files.http.mappers.FileMapper;
import com.rota.facil.files.persistence.repositories.VehicleFilesRepository;
import com.rota.facil.users.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import com.rota.facil.annotations.UseCase;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class ListVehiclePhotosUseCase {
    private final VehicleFilesRepository vehicleFilesRepository;
    private final FileMapper fileMapper;

    @Transactional(readOnly = true)
    public List<FileResponse> execute(UUID vehicleId, UserEntity currentUser) {
        UUID prefectureId = currentUser.getPrefecture().getId();

        return this.vehicleFilesRepository
                .findAllByVehicleIdAndPrefectureIdAndFileCategory(
                        vehicleId,
                        prefectureId,
                        FileCategory.VEHICLE_PHOTO
                )
                .stream()
                .map(this.fileMapper::map)
                .toList();
    }
}
