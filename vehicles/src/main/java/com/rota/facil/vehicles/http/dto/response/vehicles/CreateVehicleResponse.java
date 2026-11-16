package com.rota.facil.vehicles.http.dto.response.vehicles;

import com.rota.facil.vehicles.domain.VehicleStatus;
import com.rota.facil.vehicles.domain.VehicleType;

import java.util.UUID;

public record CreateVehicleResponse(
        UUID id,
        CreateVehicleUser driver,
        UUID prefectureId,
        Long capacity,
        String plate,
        boolean active,
        VehicleType vehicleType,
        VehicleStatus status,
        String createdAt
) {
}
