package com.rota.facil.journey.http.dto.response.trips;

import com.rota.facil.journey.domain.Shift;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public record TripRouteResponse(
        UUID id,
        String name,
        Shift shift,
        LocalTime going,
        LocalTime return_,
        LocalTime goingFinish,
        LocalTime returnFinish,
        List<TripRouteInstitutionResponse> institutions,
        List<TripRouteBoardPointResponse> boardPoints
) {
}
