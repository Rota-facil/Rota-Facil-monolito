package com.rota.facil.journey.business.trips;

import com.rota.facil.annotations.UseCase;
import com.rota.facil.journey.domain.Progress;
import com.rota.facil.journey.exceptions.DeactivateTripExceptions;
import com.rota.facil.journey.exceptions.TripNotFoundException;
import com.rota.facil.journey.persistence.entities.TripEntity;
import com.rota.facil.journey.persistence.repositories.TripRepository;
import com.rota.facil.users.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class DeactivateTripUseCase {
    private final TripRepository tripRepository;

    public void execute(UUID tripId, UserEntity currentUser) {
        TripEntity tripFound = this.tripRepository.findTripByIdAndPrefectureId(tripId, currentUser.getPrefectureId())
                .orElseThrow(TripNotFoundException::new);

        if (!tripFound.getActualStatus().equals(Progress.CANCELLED) && !tripFound.getActualStatus().equals(Progress.NOT_STARTED)) {
            throw new DeactivateTripExceptions("Só é possível desativar uma viagem que ainda não começou ou foi cancelada");
        }

        tripFound.deactivate();
        this.tripRepository.save(tripFound);
    }
}
