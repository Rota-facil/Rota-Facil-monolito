package com.rota.facil.journey.business.helpers.trips;

import com.rota.facil.journey.domain.TripOrientation;
import com.rota.facil.journey.persistence.entities.BoardPointRouteEntity;
import com.rota.facil.journey.persistence.entities.TripEntity;
import com.rota.facil.journey.persistence.repositories.RouteRepository;
import com.rota.facil.journey.persistence.repositories.TripRepository;
import com.rota.facil.journey.persistence.repositories.TripUserRepository;
import com.rota.facil.places.persistence.entitites.BoardPointEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RegisterIgnoredBoardPointsHelper {
    private final TripUserRepository tripUserRepository;
    private final RouteRepository routeRepository;

    public void execute(TripEntity trip, TripOrientation orientation) {
        List<BoardPointEntity> allRouteBoardPointsTobeVisited = this.routeRepository.findAllBoardPointsByRouteId(trip.getRoute().getId());

        List<BoardPointEntity> boardPointsByCurrentOrientation =
                (orientation.equals(TripOrientation.GOING))
                ? this.tripUserRepository.findAllBoardPointByTripIdOfStudentsGoing(trip.getId())
                : this.tripUserRepository.findAllBoardPointByTripIdOfStudentsReturn(trip.getId());


        List<BoardPointEntity> ignoredBoardPointsByCurrentOrientation = allRouteBoardPointsTobeVisited.stream()
                        .filter(
                                boardPoint -> !boardPointsByCurrentOrientation.contains(boardPoint)
                        )
                .collect(Collectors.toCollection(ArrayList::new));

        trip.setIgnoredBoardPoints(ignoredBoardPointsByCurrentOrientation);
    }
}
