package com.rota.facil.journey.business.useCases.routes;

import com.rota.facil.annotations.UseCase;
import com.rota.facil.journey.http.dto.response.routes.RouteResponse;
import com.rota.facil.journey.persistence.mappers.RouteMapper;
import com.rota.facil.journey.persistence.repositories.RouteRepository;
import com.rota.facil.users.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class ListRoutesUseCase {
    private final RouteRepository routeRepository;
    private final RouteMapper routeMapper;

    public Page<RouteResponse> execute(UserEntity currentUser, Pageable pageable) {
        return this.routeRepository.findAllByPrefectureIdAndActive(currentUser.getPrefecture().getId(), pageable)
                .map(routeMapper::map);
    }
}
