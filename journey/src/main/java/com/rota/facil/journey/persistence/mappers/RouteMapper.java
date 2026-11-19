package com.rota.facil.journey.persistence.mappers;

import com.rota.facil.journey.http.dto.request.routes.CreateRouteRequest;
import com.rota.facil.journey.http.dto.response.routes.*;
import com.rota.facil.journey.persistence.entities.BoardPointRouteEntity;
import com.rota.facil.journey.persistence.entities.InstitutionRouteEntity;
import com.rota.facil.journey.persistence.entities.RouteEntity;
import com.rota.facil.journey.persistence.entities.RouteRecurringEntity;
import com.rota.facil.places.persistence.entitites.BoardPointEntity;
import com.rota.facil.places.persistence.entitites.InstitutionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RouteMapper {
    RouteEntity map(CreateRouteRequest request);

    @Mapping(target = "institutions", expression = "java(institutionsRouteToResponse(entity.getInstitutions()))")
    @Mapping(target = "boardPoints", expression = "java(boardPointsRouteToResponse(entity.getBoardPoints()))")
    @Mapping(target = "vehicles", expression = "java(vehiclesRouteToResponse(entity.getRecurring()))")
    RouteResponse map(RouteEntity entity);

    default List<RouteInstitutionResponse> institutionsRouteToResponse(List<InstitutionRouteEntity> entities) {
        return entities.stream()
                .map(routeInstitution -> {
                    InstitutionEntity institution = routeInstitution.getInstitution();
                    return new RouteInstitutionResponse(routeInstitution.getId(), institution.getName(), institution.getLatitude(), institution.getLongitude(), routeInstitution.getInstitutionTimeGoing(), routeInstitution.getInstitutionTimeFinish());
                })
                .toList();
    }

    default List<RouteBoardPointResponse> boardPointsRouteToResponse(List<BoardPointRouteEntity> entities) {
        return entities.stream()
                .map(routesBoardPoints -> {
                    BoardPointEntity boardPoint = routesBoardPoints.getBoardPoint();
                    return new RouteBoardPointResponse(routesBoardPoints.getId(), boardPoint.getName(), boardPoint.getLatitude(), boardPoint.getLongitude(), routesBoardPoints.getBoardTimeGoing(), routesBoardPoints.getBoardTimeFinish());
                })
                .toList();
    }

    default List<RouteVehiclesResponse> vehiclesRouteToResponse(List<RouteRecurringEntity> entities) {
        return entities
                .stream()
                .map(RouteRecurringEntity::getVehicle)
                .map(vehicles -> new RouteVehiclesResponse(vehicles.getId(), vehicles.getPlate()))
                .toList();
    }
}
