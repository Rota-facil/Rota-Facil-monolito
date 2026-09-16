package com.rota.facil.vehicles.http.dto.response.vehicles;

import com.rota.facil.vehicles.domain.VehicleStatus;

import java.util.UUID;

public record CreateVehicleResponse(
        UUID id,
        CreateVehicleUser user,
        UUID prefectureId,
        Long capacity,
        String plate,
        boolean active,
        VehicleStatus status,
        String createdAt
) {
}
