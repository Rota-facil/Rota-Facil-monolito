package com.rota.facil.journey.http.dto.response.routes;

import com.rota.facil.journey.domain.DaysOfWeek;
import com.rota.facil.journey.domain.Shift;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public record RouteResponse(
        UUID id,
        String name,
        Shift shift,
        LocalTime going,
        LocalTime return_,
        LocalTime goingFinish,
        LocalTime returnFinish,
        UUID prefectureId,
        Set<DaysOfWeek> daysOfWeeks,
        LocalDateTime createdAt,
        List<RouteInstitutionResponse> institutions,
        List<RouteBoardPointResponse> boardPoints
) {
}
