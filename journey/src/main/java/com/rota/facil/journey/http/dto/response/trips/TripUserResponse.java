package com.rota.facil.journey.http.dto.response.trips;

public record TripUserResponse(
        TripUserUserResponse student,
        TripUserInstitutionResponse institution,
        TripUserBoardPointResponse boardPoint,
        Boolean going,
        Boolean return_
) {
}
