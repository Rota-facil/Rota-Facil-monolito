package com.rota.facil.journey.http.dto.response.trips;

import java.util.UUID;
import java.time.LocalTime;

public record TripRouteBoardPointResponse(
        UUID id,
        String name,
        Double latitude,
        Double longitude,
        LocalTime boardTimeGoing,
        LocalTime boardTimeFinish
) {
}
