package com.rota.facil.journey.persistence.mappers;

import com.rota.facil.journey.domain.Shift;
import com.rota.facil.journey.http.dto.response.trips.TripResponse;
import com.rota.facil.journey.http.dto.response.trips.TripRouteBoardPointResponse;
import com.rota.facil.journey.http.dto.response.trips.TripRouteInstitutionResponse;
import com.rota.facil.journey.http.dto.response.trips.TripRouteResponse;
import com.rota.facil.journey.persistence.entities.BoardPointRouteEntity;
import com.rota.facil.journey.persistence.entities.InstitutionRouteEntity;
import com.rota.facil.journey.persistence.entities.RouteEntity;
import com.rota.facil.journey.persistence.entities.TripEntity;
import com.rota.facil.places.persistence.entitites.BoardPointEntity;
import com.rota.facil.places.persistence.entitites.InstitutionEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface TripMapper {
    @Mapping(target = "route", expression = "java(mapTripRoute(entity))")
    TripResponse map(TripEntity entity);

    default TripRouteResponse mapTripRoute(TripEntity trip) {
        RouteEntity route = trip.getRoute();

        UUID routeId = route.getId();
        String routeName = route.getName();
        Shift shift = route.getShift();
        LocalTime routeGoing = route.getGoing();
        LocalTime routeReturn_ = route.getReturn_();
        LocalTime routeGoingFinish = route.getGoingFinish();
        LocalTime routeReturnFinish = route.getReturnFinish();

        List<TripRouteInstitutionResponse> institutions = this.mapInstitutions(route.getInstitutions(), trip);
        List<TripRouteBoardPointResponse> boardPoints = this.mapBoardPoints(route.getBoardPoints(), trip);

        return new TripRouteResponse(
                routeId,
                routeName,
                shift,
                routeGoing,
                routeReturn_,
                routeGoingFinish,
                routeReturnFinish,
                institutions,
                boardPoints
        );
    }

    private List<TripRouteBoardPointResponse> mapBoardPoints(List<BoardPointRouteEntity> routeBoardPoints, TripEntity trip) {
        List<BoardPointEntity> ignoredBoardPoints = trip.getIgnoredBoardPoints();

        return routeBoardPoints.stream()
                .filter(routeBoardPoint -> !ignoredBoardPoints.contains(routeBoardPoint.getBoardPoint()))
                .map(
                        routeBoardPoint -> {
                            BoardPointEntity boardPoint = routeBoardPoint.getBoardPoint();
                            return new TripRouteBoardPointResponse(
                                    boardPoint.getId(),
                                    boardPoint.getName(),
                                    boardPoint.getLatitude(),
                                    boardPoint.getLongitude(),
                                    routeBoardPoint.getBoardTimeGoing(),
                                    routeBoardPoint.getBoardTimeFinish()
                            );
                        }
                )
                .toList();
    }

    private List<TripRouteInstitutionResponse> mapInstitutions(List<InstitutionRouteEntity> routeInstitutions, TripEntity trip) {
        List<InstitutionEntity> ignoredInstitutions = trip.getIgnoredInstitutions();

        return routeInstitutions.stream()
                .filter(routeInstitution -> !ignoredInstitutions.contains(routeInstitution.getInstitution()))
                .map(
                        routeInstitution -> {
                            InstitutionEntity institution = routeInstitution.getInstitution();
                            return new TripRouteInstitutionResponse(
                                    institution.getId(),
                                    institution.getName(),
                                    institution.getLatitude(),
                                    institution.getLongitude(),
                                    routeInstitution.getInstitutionTimeGoing(),
                                    routeInstitution.getInstitutionTimeFinish()
                            );
                        }
                )
                .toList();
    }
}