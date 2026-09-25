package com.rota.facil.journey.business.helpers.trips;

import com.rota.facil.journey.domain.TripOrientation;
import com.rota.facil.journey.persistence.entities.TripEntity;
import com.rota.facil.journey.persistence.repositories.TripRepository;
import com.rota.facil.journey.persistence.repositories.TripUserRepository;
import com.rota.facil.places.persistence.entitites.BoardPointEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RegisterIgnoredBoardPointsHelper {
    private final TripUserRepository tripUserRepository;

    public void execute(TripEntity trip, TripOrientation orientation) {
        Set<BoardPointEntity> ignoredBoardPointsByCurrentOrientation =
                (orientation.equals(TripOrientation.GOING))
                ? this.tripUserRepository.findAllBoardPointByTripIdOfStudentsReturn(trip.getId())
                : this.tripUserRepository.findAllBoardPointByTripIdOfStudentsGoing(trip.getId());

        trip.setIgnoredBoardPoints(ignoredBoardPointsByCurrentOrientation);
    }
}
