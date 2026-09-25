package com.rota.facil.journey.business.helpers.trips;

import com.rota.facil.journey.domain.TripOrientation;
import com.rota.facil.journey.persistence.entities.TripEntity;
import com.rota.facil.journey.persistence.repositories.TripRepository;
import com.rota.facil.journey.persistence.repositories.TripUserRepository;
import com.rota.facil.places.persistence.entitites.InstitutionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class RegisterIgnoredInstitutionsHelper {
    private final TripUserRepository tripUserRepository;

    public void execute(TripEntity trip, TripOrientation orientation) {
        Set<InstitutionEntity> ignoredInstitutionsByCurrentOrientation =
                (orientation.equals(TripOrientation.GOING))
                ? this.tripUserRepository.findAllInstitutionsByTripIdOfStudentsReturn(trip.getId())
                : this.tripUserRepository.findAllInstitutionsByTripIdOfStudentsGoing(trip.getId());

        trip.setIgnoredInstitutions(ignoredInstitutionsByCurrentOrientation);
    }
}
