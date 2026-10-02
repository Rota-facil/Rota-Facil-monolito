package com.rota.facil.journey.business.routes;

import com.rota.facil.annotations.UseCase;
import com.rota.facil.journey.business.helpers.routes.FindRouteByIdAndPrefectureIdHelper;
import com.rota.facil.journey.domain.DaysOfWeek;
import com.rota.facil.journey.exceptions.UpdateRouteException;
import com.rota.facil.journey.http.dto.request.routes.*;
import com.rota.facil.journey.http.dto.response.routes.RouteResponse;
import com.rota.facil.journey.persistence.entities.BoardPointRouteEntity;
import com.rota.facil.journey.persistence.entities.InstitutionRouteEntity;
import com.rota.facil.journey.persistence.entities.RouteEntity;
import com.rota.facil.journey.persistence.mappers.RouteMapper;
import com.rota.facil.journey.persistence.repositories.RouteRepository;
import com.rota.facil.journey.persistence.repositories.TripRepository;
import com.rota.facil.places.persistence.entitites.BoardPointEntity;
import com.rota.facil.places.persistence.entitites.InstitutionEntity;
import com.rota.facil.places.persistence.repositories.BoardPointRepository;
import com.rota.facil.places.persistence.repositories.InstitutionRepository;
import com.rota.facil.users.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

@UseCase
@RequiredArgsConstructor
public class UpdateRouteUseCase {

    private final FindRouteByIdAndPrefectureIdHelper findRouteByIdAndPrefectureIdHelper;
    private final BoardPointRepository boardPointRepository;
    private final InstitutionRepository institutionRepository;
    private final TripRepository tripRepository;
    private final RouteRepository routeRepository;
    private final RouteMapper routeMapper;


    public RouteResponse execute(UserEntity currentUser, UpdateRouteRequest request, UUID routeId) {

        RouteEntity routeFound = this.findRouteByIdAndPrefectureIdHelper.execute(routeId, currentUser.getPrefectureId());

        if (this.tripRepository.countTripsStartedByRouteId(routeId) > 0) {
            throw new UpdateRouteException(
                    "Não é possível atualizar a rota porque ainda existem viagens em curso nesse momento"
            );
        }


        List<UUID> boardPointsId = request.boardPoints()
                .stream()
                .map(UpdateRouteBoardPointRequest::boardPointId)
                .toList();

        List<UUID> institutionsId = request.institutions()
                .stream()
                .map(UpdateRouteInstitutionRequest::institutionId)
                .toList();

        this.removeOldBoardPoints(routeFound, boardPointsId);

        this.removeOldInstitutions(routeFound, institutionsId);

        List<BoardPointRouteEntity> boardPointsToAdd = this.constructNewBoardPointsToAddRoute(routeFound, request.boardPoints(), currentUser.getPrefectureId());
        routeFound.getBoardPoints().addAll(boardPointsToAdd);
        this.updateExistingBoardPoints(routeFound, request.boardPoints());


        List<InstitutionRouteEntity> institutionsToAdd = this.constructNewInstitutionsToAddRoute(routeFound, request.institutions(), currentUser.getPrefectureId());
        routeFound.getInstitutions().addAll(institutionsToAdd);
        this.updateExistingInstitutions(routeFound, request.institutions());


        routeFound.setName(request.name());
        routeFound.setShift(request.shift());
        routeFound.setGoing(request.going());
        routeFound.setReturn_(request.return_());
        routeFound.setGoingFinish(request.goingFinish());
        routeFound.setReturnFinish(request.returnFinish());
        routeFound.setDaysOfWeek(new HashSet<>(request.daysOfWeek()));

        RouteEntity routeSaved = this.routeRepository.save(routeFound);


        return this.routeMapper.map(routeSaved);
    }


    public void removeOldBoardPoints(RouteEntity route, List<UUID> boardPointsId) {

        List<BoardPointRouteEntity> boardPointsToRemove = route.getBoardPoints()
                        .stream()
                        .filter(
                                boardPointRoute ->
                                        !boardPointsId.contains(
                                                boardPointRoute
                                                        .getBoardPoint()
                                                        .getId()
                                        )
                        )
                        .toList();

        route.getBoardPoints().removeAll(boardPointsToRemove);
    }


    public void removeOldInstitutions(RouteEntity route, List<UUID> institutionsId) {

        List<InstitutionRouteEntity> institutionsToRemove = route.getInstitutions()
                        .stream()
                        .filter(
                                institutionRoute ->
                                        !institutionsId.contains(
                                                institutionRoute
                                                        .getInstitution()
                                                        .getId()
                                        )
                        )
                        .toList();

        route.getInstitutions().removeAll(institutionsToRemove);
    }


    public List<BoardPointRouteEntity> constructNewBoardPointsToAddRoute(RouteEntity route, List<UpdateRouteBoardPointRequest> boardPointRequests, UUID prefectureId) {
        List<UUID> boardPointsIdToAdd = boardPointRequests.stream()
                        .map(UpdateRouteBoardPointRequest::boardPointId)
                        .filter(
                                boardPointId ->
                                        route.getBoardPoints()
                                                .stream()
                                                .noneMatch(
                                                        boardPointRoute ->
                                                                boardPointRoute
                                                                        .getBoardPoint()
                                                                        .getId()
                                                                        .equals(boardPointId)
                                                )
                        )
                        .toList();


        List<BoardPointEntity> boardPointsToAdd =
                this.boardPointRepository
                        .findAllByIdAndPrefectureIdAndActive(
                                boardPointsIdToAdd,
                                prefectureId
                        );

        Map<UUID, UpdateRouteBoardPointRequest> requestById = boardPointRequests.stream()
                        .collect(
                                Collectors.toMap(
                                        UpdateRouteBoardPointRequest::boardPointId,
                                        request -> request
                                )
                        );


        return boardPointsToAdd
                .stream()
                .map(
                        boardPoint -> {

                            UpdateRouteBoardPointRequest request = requestById.get(boardPoint.getId());
                            return BoardPointRouteEntity.builder()
                                    .boardPoint(boardPoint)
                                    .route(route)
                                    .boardTimeGoing(request.boardTimeGoing())
                                    .boardTimeFinish(request.boardTimeFinish())
                                    .build();
                        }
                )
                .toList();
    }

    public void updateExistingBoardPoints(RouteEntity route, List<UpdateRouteBoardPointRequest> boardPointRequests) {
        Map<UUID, UpdateRouteBoardPointRequest> requestById = boardPointRequests.stream()
                        .collect(
                                Collectors.toMap(
                                        UpdateRouteBoardPointRequest::boardPointId,
                                        request -> request
                                )
                        );


        route.getBoardPoints()
                .forEach(
                        boardPointRoute -> {

                            UUID boardPointId = boardPointRoute.getBoardPoint().getId();

                            UpdateRouteBoardPointRequest request = requestById.get(boardPointId);

                            if (request != null) {
                                boardPointRoute.setBoardTimeGoing(request.boardTimeGoing());
                                boardPointRoute.setBoardTimeFinish(request.boardTimeFinish());
                            }
                        }
                );
    }


    public List<InstitutionRouteEntity> constructNewInstitutionsToAddRoute(RouteEntity route, List<UpdateRouteInstitutionRequest> institutionRequests, UUID prefectureId) {
        List<UUID> institutionsIdToAdd = institutionRequests.stream()
                        .map(UpdateRouteInstitutionRequest::institutionId)
                        .filter(
                                institutionId ->
                                        route.getInstitutions()
                                                .stream()
                                                .noneMatch(
                                                        institutionRoute ->
                                                                institutionRoute
                                                                        .getInstitution()
                                                                        .getId()
                                                                        .equals(institutionId)
                                                )
                        )
                        .toList();


        List<InstitutionEntity> institutionsToAdd = this.institutionRepository.findAllByIdAndPrefectureIdAndActive(institutionsIdToAdd, prefectureId);

        Map<UUID, UpdateRouteInstitutionRequest> requestById =
                institutionRequests
                        .stream()
                        .collect(
                                Collectors.toMap(
                                        UpdateRouteInstitutionRequest::institutionId,
                                        request -> request
                                )
                        );


        return institutionsToAdd
                .stream()
                .map(
                        institution -> {

                            UpdateRouteInstitutionRequest request = requestById.get(institution.getId());

                            return InstitutionRouteEntity
                                    .builder()
                                    .institution(institution)
                                    .route(route)
                                    .institutionTimeGoing(request.institutionGoing())
                                    .institutionTimeFinish(request.institutionFinish())
                                    .build();
                        }
                )
                .toList();
    }


    public void updateExistingInstitutions(RouteEntity route, List<UpdateRouteInstitutionRequest> institutionRequests) {
        Map<UUID, UpdateRouteInstitutionRequest> requestById =
                institutionRequests
                        .stream()
                        .collect(
                                Collectors.toMap(
                                        UpdateRouteInstitutionRequest::institutionId,
                                        request -> request
                                )
                        );


        route.getInstitutions()
                .forEach(
                        institutionRoute -> {

                            UUID institutionId = institutionRoute.getInstitution().getId();

                            UpdateRouteInstitutionRequest request = requestById.get(institutionId);

                            if (request != null) {
                                institutionRoute.setInstitutionTimeGoing(request.institutionGoing());
                                institutionRoute.setInstitutionTimeFinish(request.institutionFinish());
                            }
                        }
                );
    }
}