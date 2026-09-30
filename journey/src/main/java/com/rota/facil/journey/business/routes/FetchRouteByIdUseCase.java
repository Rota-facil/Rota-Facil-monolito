package com.rota.facil.journey.business.routes;

import com.rota.facil.annotations.UseCase;
import com.rota.facil.journey.business.helpers.routes.FindRouteByIdAndPrefectureIdHelper;
import com.rota.facil.journey.http.dto.response.routes.RouteResponse;
import com.rota.facil.journey.persistence.entities.RouteEntity;
import com.rota.facil.journey.persistence.mappers.RouteMapper;
import com.rota.facil.journey.persistence.repositories.RouteRepository;
import com.rota.facil.users.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class FetchRouteByIdUseCase {
    private final FindRouteByIdAndPrefectureIdHelper findRouteByIdAndPrefectureIdHelper;
    private final RouteMapper routeMapper;

    public RouteResponse execute(UserEntity currentUser, UUID routeId) {
        return this.routeMapper.map(
                this.findRouteByIdAndPrefectureIdHelper.execute(routeId, currentUser.getPrefectureId())
        );
    }
}
