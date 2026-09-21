package com.rota.facil.journey.http.dto.response.trips;

import java.util.UUID;

public record TripVehicleResponse(
        UUID id,
        String plate,
        Long capacity,
        UUID prefectureId,
        TripDriverResponse driver
) {
}
