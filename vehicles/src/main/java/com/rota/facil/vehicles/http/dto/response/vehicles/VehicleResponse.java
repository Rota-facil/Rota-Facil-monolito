package com.rota.facil.vehicles.http.dto.response.vehicles;

import com.rota.facil.vehicles.domain.VehicleStatus;
import com.rota.facil.vehicles.domain.VehicleType;

import java.util.UUID;

public record VehicleResponse(
        UUID id,
        VehicleUserResponse driver,
        UUID prefectureId,
        Long capacity,
        String plate,
        boolean active,
        VehicleStatus status,
        VehicleType vehicleType,
        String createdAt
) {
}
