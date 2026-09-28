package com.rota.facil.journey.http.dto.response.trips;

import java.util.UUID;

public record TripStudentsResponse(
        UUID id,
        StudentResponse student,
        String institutionName,
        String boardPointName,
        String presence,
        Double score,
        Boolean going,
        Boolean return_
) {
}
