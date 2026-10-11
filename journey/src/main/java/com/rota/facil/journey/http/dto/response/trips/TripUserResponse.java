package com.rota.facil.journey.http.dto.response.trips;

import com.rota.facil.journey.domain.Presence;

public record TripUserResponse(
        TripUserUserResponse student,
        TripUserInstitutionResponse institution,
        TripUserBoardPointResponse boardPoint,
        Presence presence,
        Boolean going,
        Boolean return_
) {
}
