package com.rota.facil.journey.business.useCases.routes;

import com.rota.facil.annotations.UseCase;
import com.rota.facil.journey.business.helpers.routes.CreateRouteRecurringHelper;
import com.rota.facil.journey.business.helpers.routes.FindRouteByIdAndPrefectureIdHelper;
import com.rota.facil.journey.business.helpers.routes.ValidateUpdateRouteHelper;
import com.rota.facil.journey.http.exceptions.UpdateRouteException;
import com.rota.facil.journey.http.dto.request.routes.*;
import com.rota.facil.journey.http.dto.response.routes.RouteResponse;
import com.rota.facil.journey.persistence.entities.BoardPointRouteEntity;
import com.rota.facil.journey.persistence.entities.InstitutionRouteEntity;
import com.rota.facil.journey.persistence.entities.RouteEntity;
import com.rota.facil.journey.persistence.entities.RouteRecurringEntity;
import com.rota.facil.journey.persistence.mappers.RouteMapper;
import com.rota.facil.journey.persistence.repositories.RouteRecurringRepository;
import com.rota.facil.journey.persistence.repositories.RouteRepository;
import com.rota.facil.places.persistence.entitites.BoardPointEntity;
import com.rota.facil.places.persistence.entitites.InstitutionEntity;
import com.rota.facil.places.persistence.repositories.BoardPointRepository;
import com.rota.facil.places.persistence.repositories.InstitutionRepository;
import com.rota.facil.users.persistence.entities.UserEntity;
import com.rota.facil.vehicles.persistence.entities.VehicleEntity;
import com.rota.facil.vehicles.persistence.repositories.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@UseCase
@RequiredArgsConstructor
public class UpdateRouteUseCase {
    private final ValidateUpdateRouteHelper validateUpdateRouteHelper;
    private final FindRouteByIdAndPrefectureIdHelper findRouteByIdAndPrefectureIdHelper;
    private final BoardPointRepository boardPointRepository;
    private final RouteRecurringRepository routeRecurringRepository;
    private final InstitutionRepository institutionRepository;
    private final VehicleRepository vehicleRepository;
    private final RouteRepository routeRepository;
    private final RouteMapper routeMapper;

    @Transactional
    public RouteResponse execute(UserEntity currentUser, UpdateRouteRequest request, UUID routeId) {

        RouteEntity routeFound = this.findRouteByIdAndPrefectureIdHelper.execute(routeId, currentUser.getPrefecture().getId());
        this.validateUpdateRouteHelper.execute(routeId);

        List<UpdateRouteBoardPointRequest> boardPointsRequest = request.boardPoints() != null
        ? request.boardPoints()
        : List.of();

        List<UpdateRouteInstitutionRequest> institutionsRequest = request.institutions() != null
        ? request.institutions()
        : List.of();

        this.removeNotSelectedBoardPoints(routeFound, boardPointsRequest);
        this.removeNotSelectInstitutions(routeFound, institutionsRequest);

        List<BoardPointRouteEntity> boardPointsToAdd = this.constructNewBoardPointsToAddRoute(routeFound, boardPointsRequest, currentUser.getPrefecture().getId());
        routeFound.getBoardPoints().addAll(boardPointsToAdd);
        this.updateExistingBoardPoints(routeFound, boardPointsRequest);


        List<InstitutionRouteEntity> institutionsToAdd = this.constructNewInstitutionsToAddRoute(routeFound, institutionsRequest, currentUser.getPrefecture().getId());
        routeFound.getInstitutions().addAll(institutionsToAdd);
        this.updateExistingInstitutions(routeFound, institutionsRequest);


        List<VehicleEntity> allVehiclesOfRoute = this.routeRecurringRepository.findAllVehiclesByRouteId(routeId);

        List<UUID> allIdVehiclesOfRoute = allVehiclesOfRoute
                .stream()
                .map(VehicleEntity::getId)
                .toList();

        List<UUID> selectedVehicles = request.vehicles() != null
                ? request.vehicles()
                : List.of();

        this.removeNotSelectVehicles(allVehiclesOfRoute, selectedVehicles, routeId);
        this.addNewVehiclesForRoute(allIdVehiclesOfRoute, selectedVehicles, routeFound, currentUser.getPrefecture().getId());

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

    private void addNewVehiclesForRoute(List<UUID> allVehiclesOfRoute, List<UUID> selectedVehicles, RouteEntity route, UUID prefectureId) {
        List<UUID> vehiclesToAdd = selectedVehicles
                .stream()
                .filter(vehicleId -> !allVehiclesOfRoute.contains(vehicleId))
                .toList();

        if (!vehiclesToAdd.isEmpty()) {
            List<VehicleEntity> vehiclesToAddFound = this.vehicleRepository.findAllVehiclesByVehiclesIdAndPrefectureId(vehiclesToAdd, prefectureId);

            vehiclesToAddFound
                    .stream()
                    .filter(VehicleEntity::inOperation)
                    .findAny()
                    .ifPresent(
                            vehicle -> {
                                throw new UpdateRouteException("Não é possível adicionar todos os veículos selecionados a rota pois um dos veículos selecionados (" + vehicle.getPlate() + ") está em operação");
                            }
                    );

            List<RouteRecurringEntity> newRouteRecurring = vehiclesToAddFound
                    .stream()
                    .map(
                            vehicle -> RouteRecurringEntity
                                    .builder()
                                    .route(route)
                                    .vehicle(vehicle)
                                    .build()
                    )
                    .toList();

            this.routeRecurringRepository.saveAll(newRouteRecurring);
        }
    }

    private void removeNotSelectVehicles(List<VehicleEntity> allVehiclesOfRoute, List<UUID> selectedVehicles, UUID routeId) {
        List<VehicleEntity> vehiclesToRemove = allVehiclesOfRoute
                .stream()
                .filter(vehicle -> !selectedVehicles.contains(vehicle.getId()))
                .toList();

        vehiclesToRemove
                .stream()
                .filter(VehicleEntity::inOperation)
                .findAny()
                .ifPresent(
                        vehicle -> {
                            throw new UpdateRouteException("Não é possível desasociar veículo (" + vehicle.getPlate() + ") pois ele está em operação nesse momento");
                        }
                );

        if (!vehiclesToRemove.isEmpty()) this.routeRecurringRepository.deleteAllByRouteIdAndVehicles(routeId, vehiclesToRemove);
    }


    public void removeNotSelectedBoardPoints(RouteEntity route, List<UpdateRouteBoardPointRequest> boardPointsRequest) {
        List<UUID> boardPointsId = boardPointsRequest
                .stream()
                .map(UpdateRouteBoardPointRequest::boardPointId)
                .toList();

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


    public void removeNotSelectInstitutions(RouteEntity route, List<UpdateRouteInstitutionRequest> institutionsRequest) {
        List<UUID> institutionsId = institutionsRequest
                .stream()
                .map(UpdateRouteInstitutionRequest::institutionId)
                .toList();


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