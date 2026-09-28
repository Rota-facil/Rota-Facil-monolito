package com.rota.facil.journey.business.helpers.trips;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
public class IsTimeInIntervalHelper {

    public boolean execute(LocalTime targetTime, LocalTime startTime, LocalTime endTime) {
        LocalDate now = LocalDate.now();
        LocalDateTime startWithTol = LocalDateTime.of(now, startTime);
        LocalDateTime endWithTol = LocalDateTime.of(now, endTime);
        LocalDateTime arrivalDateTime = LocalDateTime.of(now, targetTime);
        return !arrivalDateTime.isBefore(startWithTol.minusMinutes(6L)) && !arrivalDateTime.isAfter(endWithTol.plusMinutes(6L));
    }
}
