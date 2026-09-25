package com.rota.facil.vehicles.business;

import com.rota.facil.users.persistence.entities.UserEntity;
import com.rota.facil.vehicles.business.helpers.FindDriverForVehicleUpdateHelper;
import com.rota.facil.vehicles.business.helpers.FindVehicleByIdHelper;
import com.rota.facil.vehicles.domain.UpdateVehicleData;
import com.rota.facil.vehicles.entities.VehicleEntity;
import com.rota.facil.vehicles.http.dto.request.vehicles.UpdateVehicleRequest;
import com.rota.facil.vehicles.http.dto.response.vehicles.UpdateVehicleResponse;
import com.rota.facil.vehicles.persistence.mappers.VehicleMapper;
import com.rota.facil.vehicles.persistence.repositories.VehicleRepository;
import lombok.RequiredArgsConstructor;
import com.rota.facil.annotations.UseCase;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class UpdateVehicleUseCase {
    private final FindVehicleByIdHelper findVehicleByIdHelper;
    private final FindDriverForVehicleUpdateHelper findDriverForVehicleUpdateHelper;
    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;

    @Transactional
    public UpdateVehicleResponse execute(UUID vehicleId, UpdateVehicleRequest request, UserEntity currentUser) {
        VehicleEntity vehicle = findVehicleByIdHelper.execute(vehicleId, currentUser.getPrefectureId());
        UserEntity driver = findDriverForVehicleUpdateHelper.execute(request.driverId(), currentUser.getPrefectureId(), vehicle);

        UpdateVehicleData updateData = vehicleMapper.map(request, driver);
        vehicle.update(updateData);

        return vehicleMapper.mapToUpdateResponse(vehicleRepository.save(vehicle));
    }
}
