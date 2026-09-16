package com.rota.facil.vehicles.business;

import com.rota.facil.users.entities.UserEntity;
import com.rota.facil.vehicles.entities.VehicleEntity;
import com.rota.facil.vehicles.http.dto.request.vehicles.CreateVehicleRequest;
import com.rota.facil.vehicles.http.dto.response.vehicles.CreateVehicleResponse;
import com.rota.facil.vehicles.persistence.mappers.VehicleMapper;
import com.rota.facil.vehicles.persistence.repositories.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateVehicleUseCase {
    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;

    public CreateVehicleResponse execute(CreateVehicleRequest request, UserEntity currentUser) {
        VehicleEntity preSaved = this.vehicleMapper.map(request);
        preSaved.setVechileType(request.vehicleType());
        preSaved.setPrefectureId(currentUser.getPrefectureId());

        return this.vehicleMapper.map(this.vehicleRepository.save(preSaved));
    }
}
