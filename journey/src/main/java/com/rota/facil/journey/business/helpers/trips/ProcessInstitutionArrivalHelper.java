package com.rota.facil.journey.business.helpers.trips;

import com.rota.facil.journey.domain.Delay;
import com.rota.facil.journey.domain.Progress;
import com.rota.facil.journey.persistence.entities.InstitutionRouteEntity;
import com.rota.facil.journey.persistence.entities.InstitutionVisitedEntity;
import com.rota.facil.journey.persistence.entities.RouteEntity;
import com.rota.facil.journey.persistence.entities.TripEntity;
import com.rota.facil.journey.persistence.repositories.*;
import com.rota.facil.places.http.exceptions.InstitutionNotFoundException;
import com.rota.facil.places.persistence.entitites.InstitutionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProcessInstitutionArrivalHelper {
    private final CalculateDelayHelper calculateDelayHelper;
    private final InstitutionVisitedRepository institutionVisitedRepository;
    private final IsTimeInIntervalHelper isTimeInIntervalHelper;
    private final TripRepository tripRepository;
    private final TripUserRepository tripUserRepository;
    private final TripStatusRepository tripStatusRepository;
    private final RouteRepository routeRepository;

    public void execute(TripEntity trip, InstitutionEntity institution, LocalDateTime arrivalDate) {
        RouteEntity route = trip.getRoute();

        // preciso saber se estou na ida ou na volta
        boolean isGoing = !this.tripStatusRepository.existsByTripIdAndProgress(trip.getId(), Progress.STARTED_FINISHED)
                && this.isTimeInIntervalHelper.execute(arrivalDate.toLocalTime(), route.getGoing(), route.getGoingFinish());

        boolean isReturn = this.tripStatusRepository.existsByTripIdAndProgress(trip.getId(), Progress.RETURN_STARTED)
                && this.isTimeInIntervalHelper.execute(arrivalDate.toLocalTime(), route.getReturn_(), route.getReturnFinish());


        InstitutionVisitedEntity institutionVisited = this.institutionVisitedRepository.findByInstitutionIdAndTripId(institution.getId(), trip.getId())
                .orElseGet(
                        () -> InstitutionVisitedEntity.builder()
                                .institution(institution)
                                .trip(trip)
                                .build()
                );

        if (isGoing) this.processGoing(trip, institution, institutionVisited);
        if (isReturn) this.processReturn(trip, institution, institutionVisited);
    }

    private void processGoing(TripEntity trip, InstitutionEntity institution, InstitutionVisitedEntity institutionVisited) {
        if (institutionVisited.isGoing() || trip.getIgnoredInstitutions().contains(institution)) return;

        institutionVisited.setGoing(true);

        List<InstitutionEntity> AllInstitutionsToBeVisitedInGoing = this.tripUserRepository.findAllInstitutionsByTripIdOfStudentsGoing(trip.getId());
        List<InstitutionEntity> AllInstitutionsVisitedInGoing = this.institutionVisitedRepository.findAllInstitutionsByTripId(trip.getId());

        InstitutionRouteEntity institutionRouteFind = this.routeRepository.findInstitutionByRouteIdAndInstitutionId(trip.getRoute().getId(), institutionVisited.getInstitution().getId())
                .orElseThrow(InstitutionNotFoundException::new);

        Delay delayInstitutionArrival = this.calculateDelayHelper.execute(institutionRouteFind.getInstitutionTimeGoing(), LocalTime.now());
        trip.addNewStatus(Progress.INSTITUTION_ARRIVAL, institution.getName(), delayInstitutionArrival);
        this.tripRepository.save(trip);

        if (AllInstitutionsToBeVisitedInGoing.size() != AllInstitutionsVisitedInGoing.size()) return;


        Delay delayGoingFinish = this.calculateDelayHelper.execute(trip.getRoute().getGoingFinish(), LocalTime.now());
        trip.addNewStatus(Progress.INSTITUTION_ARRIVAL, institution.getName(), delayGoingFinish);
        tripUserRepository.setAbsentsGoingStudentsByTripId(trip.getId());
        this.tripRepository.save(trip);
    }

    private void processReturn(TripEntity trip, InstitutionEntity institution, InstitutionVisitedEntity institutionVisited) {
        if (institutionVisited.isReturn_() || trip.getIgnoredInstitutions().contains(institution)) return;

        institutionVisited.setReturn_(true);

        Delay delayReturn = this.calculateDelayHelper.execute(trip.getRoute().getGoingFinish(), LocalTime.now());
        trip.addNewStatus(Progress.INSTITUTION_ARRIVAL, institution.getName(), delayReturn);
        tripRepository.save(trip);
    }
}
