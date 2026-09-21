package com.rota.facil.journey.http.dto.response.trips;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record TripResponse(
        UUID id,
        String name,
        String reasonOfCancellation,
        Long students,
        Double latitude,
        Double longitude,
        LocalDate createdAt,
        TripVehicleResponse vehicle,
        TripRouteResponse route,
        String actualStatus,
        List<TripStatusResponseDTO> tripStatus
) {
}
