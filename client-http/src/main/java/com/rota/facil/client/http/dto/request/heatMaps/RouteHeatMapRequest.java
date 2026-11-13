package com.rota.facil.client.http.dto.request.heatMaps;

import java.util.List;
import java.util.UUID;

public record RouteHeatMapRequest(
        RouteHeatMapCurrentUserRequest currentUser,
        UUID routeId,
        List<PointRequest> points

) {
}
