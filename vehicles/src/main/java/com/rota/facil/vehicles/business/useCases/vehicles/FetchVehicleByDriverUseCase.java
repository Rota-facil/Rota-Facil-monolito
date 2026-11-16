package com.rota.facil.vehicles.business.useCases.vehicles;

import com.rota.facil.annotations.UseCase;
import com.rota.facil.users.persistence.entities.UserEntity;
import com.rota.facil.vehicles.http.dto.response.vehicles.VehicleResponse;
import com.rota.facil.vehicles.http.exceptions.VehicleNotFoundException;
import com.rota.facil.vehicles.persistence.entities.VehicleEntity;
import com.rota.facil.vehicles.persistence.mappers.VehicleMapper;
import com.rota.facil.vehicles.persistence.repositories.VehicleRepository;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class FetchVehicleByDriverUseCase {
    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;

    public VehicleResponse execute(UserEntity currentUser, UUID driverId) {
        VehicleEntity vehicleFound = this.vehicleRepository.findByDriverIdAndPrefectureId(driverId, currentUser.getPrefecture().getId())
                .orElseThrow(VehicleNotFoundException::new);

        return this.vehicleMapper.mapResponse(vehicleFound);
    }
}
