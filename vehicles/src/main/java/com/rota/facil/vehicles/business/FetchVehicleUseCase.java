package com.rota.facil.vehicles.business;

import com.rota.facil.users.persistence.entities.UserEntity;
import com.rota.facil.vehicles.business.helpers.FindVehicleByIdHelper;
import com.rota.facil.vehicles.entities.VehicleEntity;
import com.rota.facil.vehicles.http.dto.response.vehicles.FetchVehicleResponse;
import com.rota.facil.vehicles.persistence.mappers.VehicleMapper;
import lombok.RequiredArgsConstructor;
import com.rota.facil.annotations.UseCase;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class FetchVehicleUseCase {
    private final FindVehicleByIdHelper findVehicleByIdHelper;
    private final VehicleMapper vehicleMapper;

    public FetchVehicleResponse execute(UUID vehicleId, UserEntity currentUser) {
        VehicleEntity vehicle = findVehicleByIdHelper.execute(vehicleId, currentUser.getPrefectureId());
        return vehicleMapper.mapToFetchResponse(vehicle);
    }
}
