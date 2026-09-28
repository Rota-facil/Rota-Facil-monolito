package com.rota.facil.journey.business.routes;

import com.rota.facil.journey.business.helpers.routes.CreateRouteRecurringHelper;
import com.rota.facil.journey.http.dto.request.routes.CreateBoardPointRouteRequestDTO;
import com.rota.facil.journey.http.dto.request.routes.CreateInstitutionRouteRequestDTO;
import com.rota.facil.journey.http.dto.request.routes.CreateRouteRequest;
import com.rota.facil.journey.http.dto.response.routes.CreateRouteResponse;
import com.rota.facil.journey.persistence.entities.BoardPointRouteEntity;
import com.rota.facil.journey.persistence.entities.InstitutionRouteEntity;
import com.rota.facil.journey.persistence.entities.RouteEntity;
import com.rota.facil.journey.persistence.mappers.RouteMapper;
import com.rota.facil.journey.persistence.repositories.RouteRepository;
import com.rota.facil.places.persistence.entitites.BoardPointEntity;
import com.rota.facil.places.persistence.entitites.InstitutionEntity;
import com.rota.facil.places.persistence.repositories.BoardPointRepository;
import com.rota.facil.places.persistence.repositories.InstitutionRepository;
import com.rota.facil.users.persistence.entities.UserEntity;
import com.rota.facil.places.http.exceptions.BoardPointNotFoundException;
import com.rota.facil.places.http.exceptions.InstitutionNotFoundException;
import lombok.RequiredArgsConstructor;
import com.rota.facil.annotations.UseCase;

import java.util.*;
import java.util.stream.Collectors;

@UseCase
@RequiredArgsConstructor
public class CreateRouteUseCase {
    private final CreateRouteRecurringHelper createRouteRecurringHelper;
    private final RouteRepository routeRepository;
    private final InstitutionRepository institutionRepository;
    private final BoardPointRepository boardPointRepository;
    private final RouteMapper routeMapper;

    public CreateRouteResponse execute(UserEntity currentUser, CreateRouteRequest request) {
        RouteEntity preSaved = this.routeMapper.map(request);
        preSaved.setPrefectureId(currentUser.getPrefectureId());


        List<InstitutionEntity> institutionsFound = this.findAllInstitutions(request.institutions().stream().map(CreateInstitutionRouteRequestDTO::institutionId).toList());
        List<BoardPointEntity> boardPointsFound = this.findAllBoardPoints(request.boardPoints().stream().map(CreateBoardPointRouteRequestDTO::boardPointId).toList());

        this.setInstitutions(preSaved, institutionsFound, request.institutions());
        this.setBoardPoints(preSaved, boardPointsFound, request.boardPoints());

        RouteEntity saved = this.routeRepository.save(preSaved);

        this.createRouteRecurringHelper.execute(saved, request.vehicles());

        return this.routeMapper.map(saved);
    }

    public List<InstitutionEntity> findAllInstitutions(List<UUID> institutionsId) {
        List<InstitutionEntity> institutionsFound = this.institutionRepository.findAllById(institutionsId);

        if (institutionsFound.size() != institutionsId.size()) throw new InstitutionNotFoundException("Uma das instituições selecionadas não foi encontrada");
        return institutionsFound;
    }

    public List<BoardPointEntity> findAllBoardPoints(List<UUID> boardPointsId) {
        List<BoardPointEntity> boardPointsFound = this.boardPointRepository.findAllById(boardPointsId);

        if (boardPointsFound.size() != boardPointsId.size()) throw new BoardPointNotFoundException("Um dos pontos de embarque selecionados não foi encontrado");
        return boardPointsFound;
    }

    public void setInstitutions(RouteEntity route, List<InstitutionEntity> institutions, List<CreateInstitutionRouteRequestDTO> request) {
        List<UUID> institutionsId = institutions.stream().map(InstitutionEntity::getId).toList();
        Map<UUID, InstitutionEntity> uuidInstitutionEntityMap = institutions.stream().collect(Collectors.toMap(InstitutionEntity::getId, institution -> institution));
        Map<UUID, CreateInstitutionRouteRequestDTO> uuidCreateInstitutionRouteRequestDTOMap = request.stream().collect(Collectors.toMap(CreateInstitutionRouteRequestDTO::institutionId, institution -> institution));

        for (UUID institutionId : institutionsId) {
            route.getInstitutions().add(
                    InstitutionRouteEntity.builder()
                            .institutionTimeGoing(uuidCreateInstitutionRouteRequestDTOMap.get(institutionId).institutionGoing())
                            .institutionTimeFinish(uuidCreateInstitutionRouteRequestDTOMap.get(institutionId).institutionFinish())
                            .institution(uuidInstitutionEntityMap.get(institutionId))
                            .route(route)
                            .build()
            );
        }

    }

    public void setBoardPoints(RouteEntity route, List<BoardPointEntity> boardPoints, List<CreateBoardPointRouteRequestDTO> request) {
        List<UUID> boardPointsId = boardPoints.stream().map(BoardPointEntity::getId).toList();
        Map<UUID, BoardPointEntity> uuidBoardPointEntityMap = boardPoints.stream().collect(Collectors.toMap(BoardPointEntity::getId, boardPoint -> boardPoint));
        Map<UUID, CreateBoardPointRouteRequestDTO> uuidCreateBoardPointRouteRequestDTOMap = request.stream().collect(Collectors.toMap(CreateBoardPointRouteRequestDTO::boardPointId, boardPoint -> boardPoint));

        route.setBoardPoints(new ArrayList<>());

        for (UUID boardPointId : boardPointsId) {
            route.getBoardPoints().add(
                    BoardPointRouteEntity.builder()
                            .boardTimeGoing(uuidCreateBoardPointRouteRequestDTOMap.get(boardPointId).boardTimeGoing())
                            .boardTimeFinish(uuidCreateBoardPointRouteRequestDTOMap.get(boardPointId).boardTimeFinish())
                            .boardPoint(uuidBoardPointEntityMap.get(boardPointId))
                            .route(route)
                            .build()
            );
        }
    }
}
