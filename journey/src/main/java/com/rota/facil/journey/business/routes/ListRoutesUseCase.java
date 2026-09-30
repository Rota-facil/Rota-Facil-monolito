package com.rota.facil.journey.business.routes;

import com.rota.facil.annotations.UseCase;
import com.rota.facil.journey.http.dto.response.routes.RouteResponse;
import com.rota.facil.journey.persistence.mappers.RouteMapper;
import com.rota.facil.journey.persistence.repositories.RouteRepository;
import com.rota.facil.users.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class ListRoutesUseCase {
    private final RouteRepository routeRepository;
    private final RouteMapper routeMapper;

    public List<RouteResponse> execute(UserEntity currentUser) {
        return this.routeRepository.findAllByPrefectureId(currentUser.getPrefectureId())
                .stream()
                .map(routeMapper::map)
                .toList();
    }
}
