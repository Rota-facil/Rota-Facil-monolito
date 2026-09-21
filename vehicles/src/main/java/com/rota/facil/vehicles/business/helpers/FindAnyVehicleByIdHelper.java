package com.rota.facil.vehicles.business.helpers;

import com.rota.facil.vehicles.entities.VehicleEntity;
import com.rota.facil.vehicles.http.exceptions.VehicleNotFoundException;
import com.rota.facil.vehicles.persistence.repositories.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FindAnyVehicleByIdHelper {
    private final VehicleRepository vehicleRepository;

    public VehicleEntity execute(UUID vehicleId, UUID prefectureId) {
        return vehicleRepository.findByIdAndPrefectureId(vehicleId, prefectureId)
                .orElseThrow(() -> new VehicleNotFoundException("Veículo não foi encontrado"));
    }
}
