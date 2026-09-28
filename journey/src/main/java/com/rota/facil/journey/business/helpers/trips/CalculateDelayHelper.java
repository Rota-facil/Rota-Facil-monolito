package com.rota.facil.journey.business.helpers.trips;

import com.rota.facil.journey.domain.Delay;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class CalculateDelayHelper {
    public Delay execute(LocalTime expectedTime, LocalTime actualTime) {
        if (actualTime.equals(expectedTime)) return Delay.PUNCTUAL;
        if (actualTime.isBefore(expectedTime)) return Delay.EARLY;
        if (actualTime.isAfter(expectedTime)) return Delay.LATE;

        throw new RuntimeException("Erro ao processar delay");
    }
}
