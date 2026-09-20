package com.rota.facil.journey.persistence.mappers;

import com.rota.facil.journey.http.dto.request.routes.CreateRouteRequest;
import com.rota.facil.journey.http.dto.response.routes.CreateRouteResponse;
import com.rota.facil.journey.persistence.entities.RouteEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RouteMapper {
    RouteEntity map(CreateRouteRequest request);
    CreateRouteResponse map(RouteEntity entity);
}
