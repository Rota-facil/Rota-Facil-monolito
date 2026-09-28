package com.rota.facil.journey.business.helpers.trips;

import com.rota.facil.journey.persistence.entities.TripEntity;
import com.rota.facil.places.persistence.entitites.BoardPointEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ProcessBoardPointArrivalHelper {
    public void execute(TripEntity tripFound, BoardPointEntity boardPoint, LocalDateTime now) {

    }
}
