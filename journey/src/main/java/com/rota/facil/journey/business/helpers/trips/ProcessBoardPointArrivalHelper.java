package com.rota.facil.journey.business.helpers.trips;

import com.rota.facil.journey.domain.Delay;
import com.rota.facil.journey.domain.Progress;
import com.rota.facil.journey.persistence.entities.BoardPointRouteEntity;
import com.rota.facil.journey.persistence.entities.BoardPointVisitedEntity;
import com.rota.facil.journey.persistence.entities.InstitutionVisitedEntity;
import com.rota.facil.journey.persistence.entities.TripEntity;
import com.rota.facil.journey.persistence.repositories.BoardPointVisitedRepository;
import com.rota.facil.journey.persistence.repositories.InstitutionVisitedRepository;
import com.rota.facil.journey.persistence.repositories.TripRepository;
import com.rota.facil.journey.persistence.repositories.TripStatusRepository;
import com.rota.facil.places.persistence.entitites.BoardPointEntity;
import com.rota.facil.places.persistence.entitites.InstitutionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProcessBoardPointArrivalHelper {
    private final BoardPointVisitedRepository boardPointVisitedRepository;
    private final InstitutionVisitedRepository institutionVisitedRepository;
    private final TripStatusRepository tripStatusRepository;
    private final TripRepository tripRepository;
    private final IsTimeInIntervalHelper isTimeInIntervalHelper;
    private final CalculateDelayHelper calculateDelayHelper;

    public void execute(TripEntity trip, BoardPointEntity boardPoint, LocalDateTime arrivalDate) {
        var route = trip.getRoute();
        boolean isGoing = !tripStatusRepository.existsByTripIdAndProgress(trip.getId(), Progress.STARTED_FINISHED)
                && isTimeInIntervalHelper.execute(arrivalDate.toLocalTime(), route.getGoing(), route.getGoingFinish());
        boolean isReturn = tripStatusRepository.existsByTripIdAndProgress(trip.getId(), Progress.RETURN_STARTED)
                && isTimeInIntervalHelper.execute(arrivalDate.toLocalTime(), route.getReturn_(), route.getReturnFinish());
        if (!isGoing && !isReturn) return;

        BoardPointVisitedEntity visited = boardPointVisitedRepository.findByBoardPointIdAndTripId(boardPoint.getId(), trip.getId())
                .orElseGet(() -> BoardPointVisitedEntity.builder().boardPoint(boardPoint).trip(trip).build());
        if (trip.getIgnoredBoardPoints().contains(boardPoint)) return;

        BoardPointRouteEntity routeBoardPoint = route.getBoardPoints().stream()
                .filter(item -> item.getBoardPoint().getId().equals(boardPoint.getId()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Ponto de embarque não pertence à rota da viagem"));

        if (isGoing) processGoing(trip, boardPoint, routeBoardPoint, visited, arrivalDate);
        if (isReturn) processReturn(trip, boardPoint, routeBoardPoint, visited, arrivalDate);
    }

    public void processGoing(TripEntity trip, BoardPointEntity boardPoint, BoardPointRouteEntity routeBoardPoint,
                             BoardPointVisitedEntity visited, LocalDateTime arrivalDate) {
        if (visited.isGoing() || trip.getIgnoredBoardPoints().contains(boardPoint)) return;

        visited.setGoing(true);
        boardPointVisitedRepository.save(visited);
        Delay delay = calculateDelayHelper.execute(routeBoardPoint.getBoardTimeGoing(), arrivalDate.toLocalTime());
        trip.addNewStatus(Progress.BOARD_POINT_ARRIVAL, boardPoint.getName(), delay);
        tripRepository.save(trip);
    }

    public void processReturn(TripEntity trip, BoardPointEntity boardPoint, BoardPointRouteEntity routeBoardPoint,
                              BoardPointVisitedEntity visited, LocalDateTime arrivalDate) {
        if (visited.isReturn_() || trip.getIgnoredBoardPoints().contains(boardPoint)) return;

        visited.setReturn_(true);
        boardPointVisitedRepository.save(visited);
        Delay delay = calculateDelayHelper.execute(routeBoardPoint.getBoardTimeFinish(), arrivalDate.toLocalTime());
        trip.addNewStatus(Progress.BOARD_POINT_ARRIVAL, boardPoint.getName(), delay);

        if (!tripStatusRepository.existsByTripIdAndProgress(trip.getId(), Progress.RETURN_FINISHED)
                && allInstitutionsAndBoardPointsVisitedInReturn(trip)) {
            Delay returnDelay = calculateDelayHelper.execute(trip.getRoute().getReturnFinish(), arrivalDate.toLocalTime());
            trip.addNewStatus(Progress.RETURN_FINISHED, returnDelay);
            trip.setActualStatus(Progress.RETURN_FINISHED);
        }

        tripRepository.save(trip);
    }

    private boolean allInstitutionsAndBoardPointsVisitedInReturn(TripEntity trip) {
        Set<UUID> requiredBoardPointIds = trip.getRoute().getBoardPoints().stream()
                .map(BoardPointRouteEntity::getBoardPoint)
                .map(BoardPointEntity::getId)
                .filter(id -> trip.getIgnoredBoardPoints().stream().noneMatch(ignored -> ignored.getId().equals(id)))
                .collect(Collectors.toSet());
        Set<UUID> visitedBoardPointIds = boardPointVisitedRepository.findAllReturnedByTripId(trip.getId()).stream()
                .map(BoardPointVisitedEntity::getBoardPoint)
                .map(BoardPointEntity::getId)
                .collect(Collectors.toSet());

        Set<UUID> requiredInstitutionIds = trip.getRoute().getInstitutions().stream()
                .map(institutionRoute -> institutionRoute.getInstitution().getId())
                .filter(id -> trip.getIgnoredInstitutions().stream().noneMatch(ignored -> ignored.getId().equals(id)))
                .collect(Collectors.toSet());
        Set<UUID> visitedInstitutionIds = institutionVisitedRepository.findAllReturnedByTripId(trip.getId()).stream()
                .map(InstitutionVisitedEntity::getInstitution)
                .map(InstitutionEntity::getId)
                .collect(Collectors.toSet());

        return visitedBoardPointIds.containsAll(requiredBoardPointIds)
                && visitedInstitutionIds.containsAll(requiredInstitutionIds);
    }
}
