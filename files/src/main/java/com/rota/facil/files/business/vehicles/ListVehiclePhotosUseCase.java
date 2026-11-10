package com.rota.facil.files.business.vehicles;

import com.rota.facil.files.domain.FileCategory;
import com.rota.facil.files.http.dto.response.FileResponse;
import com.rota.facil.files.http.mappers.FileMapper;
import com.rota.facil.files.persistence.repositories.FileRepository;
import com.rota.facil.users.persistence.entities.UserEntity;
import com.rota.facil.vehicles.business.helpers.FindVehicleByIdHelper;
import lombok.RequiredArgsConstructor;
import com.rota.facil.annotations.UseCase;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class ListVehiclePhotosUseCase {
    private final FindVehicleByIdHelper findVehicleByIdHelper;
    private final FileRepository fileRepository;
    private final FileMapper fileMapper;

    @Transactional(readOnly = true)
    public List<FileResponse> execute(UUID vehicleId, UserEntity currentUser) {
        UUID prefectureId = currentUser.getPrefecture().getId();
        findVehicleByIdHelper.execute(vehicleId, prefectureId);
        return fileRepository
                .findAllByOwnerIdAndPrefectureIdAndFileCategoryOrderByCreatedAtDesc(
                        vehicleId,
                        prefectureId,
                        FileCategory.PHOTO
                )
                .stream()
                .map(fileMapper::map)
                .toList();
    }
}
