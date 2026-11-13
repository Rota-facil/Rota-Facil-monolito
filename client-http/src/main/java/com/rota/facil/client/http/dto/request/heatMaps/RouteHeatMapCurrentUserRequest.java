package com.rota.facil.client.http.dto.request.heatMaps;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record RouteHeatMapCurrentUserRequest(
        @JsonProperty(value = "userId") UUID id,
        UUID prefectureId,
        String email,
        String role
) {
}
