package com.rota.facil.journey.http.dto.request.trips;

import java.util.UUID;

public record JoinInTripRequest(
        UUID boardPointId,
        UUID institutionId,
        boolean going,
        boolean return_
) {
}
