package com.rota.facil.journey.business.trips;

import com.rota.facil.journey.domain.DaysOfWeek;
import com.rota.facil.journey.persistence.entities.RouteRecurringEntity;
import com.rota.facil.journey.persistence.entities.TripEntity;
import com.rota.facil.journey.persistence.repositories.RouteRecurringRepository;
import com.rota.facil.journey.persistence.repositories.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateScheduleTrip {
    private final TripRepository tripRepository;
    private final RouteRecurringRepository routeRecurringRepository;


    @Scheduled(cron = "0 0 0 * * *", zone = "America/Sao_Paulo")
    public void execute() {
        List<RouteRecurringEntity> routeRecurrings = this.routeRecurringRepository.findAllRecurringToday(
                DaysOfWeek.getFromValueDay(
                        LocalDate.now()
                                .getDayOfWeek()
                                .getValue()
                )
        );

        if (routeRecurrings.isEmpty()) return;

        List<TripEntity> createdTrips = new ArrayList<>();

        for (RouteRecurringEntity recurring : routeRecurrings) createdTrips.add(this.createTripEntity(recurring));


        this.tripRepository.saveAll(createdTrips);
    }

    public TripEntity createTripEntity(RouteRecurringEntity recurring) {
        return TripEntity
                .builder()
                .name(recurring.getRoute().getName())
                .prefectureId(recurring.getRoute().getPrefectureId())
                .vehicle(recurring.getVehicle())
                .route(recurring.getRoute())
                .build();
    }
}
