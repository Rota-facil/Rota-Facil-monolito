package com.rota.facil.journey.business.helpers.routes;

import com.rota.facil.journey.exceptions.RouteNotFoundException;
import com.rota.facil.journey.persistence.entities.RouteEntity;
import com.rota.facil.journey.persistence.repositories.RouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FindRouteByIdHelper {
    private final RouteRepository routeRepository;

    public RouteEntity execute(UUID routeId) {
        return routeRepository.findById(routeId)
                .orElseThrow(RouteNotFoundException::new);
    }
}
