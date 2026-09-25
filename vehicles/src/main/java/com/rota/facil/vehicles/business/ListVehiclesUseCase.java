package com.rota.facil.vehicles.business;

import com.rota.facil.users.persistence.entities.UserEntity;
import com.rota.facil.vehicles.http.dto.response.vehicles.ListVehicleResponse;
import com.rota.facil.vehicles.persistence.mappers.VehicleMapper;
import com.rota.facil.vehicles.persistence.repositories.VehicleRepository;
import lombok.RequiredArgsConstructor;
import com.rota.facil.annotations.UseCase;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class ListVehiclesUseCase {
    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;

    public List<ListVehicleResponse> execute(UserEntity currentUser) {
        return vehicleRepository.findAllByPrefectureIdAndActiveTrue(currentUser.getPrefectureId())
                .stream()
                .map(vehicleMapper::mapToListResponse)
                .toList();
    }
}
