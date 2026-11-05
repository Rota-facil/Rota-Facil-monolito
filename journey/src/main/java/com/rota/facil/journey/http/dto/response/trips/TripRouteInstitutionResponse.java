package com.rota.facil.journey.http.dto.response.trips;

import java.time.LocalTime;
import java.util.UUID;

public record TripRouteInstitutionResponse(
        UUID id,
        String name,
        Double latitude,
        Double longitude,
        LocalTime institutionTimeGoing,
        LocalTime institutionTimeFinish
) {
}
