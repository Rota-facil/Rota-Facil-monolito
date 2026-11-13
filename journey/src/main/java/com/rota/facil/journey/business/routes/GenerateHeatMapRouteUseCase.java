package com.rota.facil.journey.business.routes;

import com.rota.facil.annotations.UseCase;
import com.rota.facil.client.http.controllers.IntelligenceHttpClient;
import com.rota.facil.client.http.dto.request.heatMaps.PointRequest;
import com.rota.facil.client.http.dto.request.heatMaps.RouteHeatMapCurrentUserRequest;
import com.rota.facil.client.http.dto.request.heatMaps.RouteHeatMapRequest;
import com.rota.facil.journey.exceptions.RouteNotFoundException;
import com.rota.facil.journey.persistence.entities.RouteEntity;
import com.rota.facil.journey.persistence.repositories.RouteRepository;
import com.rota.facil.journey.persistence.repositories.TripUserRepository;
import com.rota.facil.users.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class GenerateHeatMapRouteUseCase {
    private final TripUserRepository tripUserRepository;
    private final IntelligenceHttpClient intelligenceHttpClient;
    private final RouteRepository routeRepository;

    public void execute(UUID routeId, UserEntity currentUser) {
        RouteEntity routeFound = this.routeRepository.findById(routeId)
                .orElseThrow(RouteNotFoundException::new);


        RouteHeatMapCurrentUserRequest currentUserRequest = new RouteHeatMapCurrentUserRequest(
                currentUser.getId(),
                currentUser.getPrefecture().getId(),
                currentUser.getEmail(),
                currentUser.getRole().name()
        );

        List<PointRequest> pointsRequest = this.tripUserRepository.findAllStudentsBoardPointsPointsSelectByRouteId(routeId);


        this.intelligenceHttpClient.generateHeatMapRoute(
                new RouteHeatMapRequest(
                        currentUserRequest,
                        routeId,
                        pointsRequest
                )
        );
    }
}
