package com.rota.facil.journey.business.helpers.trips;

import com.rota.facil.journey.domain.TripOrientation;
import com.rota.facil.journey.persistence.entities.TripEntity;
import com.rota.facil.journey.persistence.repositories.RouteRepository;
import com.rota.facil.journey.persistence.repositories.TripRepository;
import com.rota.facil.journey.persistence.repositories.TripUserRepository;
import com.rota.facil.places.persistence.entitites.InstitutionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RegisterIgnoredInstitutionsHelper {
    private final TripUserRepository tripUserRepository;
    private final RouteRepository routeRepository;

    public void execute(TripEntity trip, TripOrientation orientation) {
        List<InstitutionEntity> allInstitutionsTobeVisited = this.routeRepository.findAllInstitutionsByRouteId(trip.getRoute().getId());

        List<InstitutionEntity> institutionsByCurrentOrientation =
                (orientation.equals(TripOrientation.GOING))
                ? this.tripUserRepository.findAllInstitutionsByTripIdOfStudentsGoing(trip.getId())
                : this.tripUserRepository.findAllInstitutionsByTripIdOfStudentsReturn(trip.getId());

        List<InstitutionEntity> ignoredInstitutionsByCurrentOrientation = allInstitutionsTobeVisited.stream()
                        .filter(
                                institution -> !institutionsByCurrentOrientation.contains(institution)
                        )
                        .collect(Collectors.toCollection(ArrayList::new));

        trip.setIgnoredInstitutions(ignoredInstitutionsByCurrentOrientation);
    }
}
