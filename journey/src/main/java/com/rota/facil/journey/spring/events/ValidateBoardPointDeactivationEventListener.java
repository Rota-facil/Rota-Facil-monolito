package com.rota.facil.journey.spring.events;

import com.rota.facil.journey.http.exceptions.BoardPointInActiveTripException;
import com.rota.facil.journey.persistence.repositories.TripRepository;
import com.rota.facil.places.spring.events.dto.ValidateBoardPointDeactivationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidateBoardPointDeactivationEventListener {
    private final TripRepository tripRepository;

    @EventListener
    public void execute(ValidateBoardPointDeactivationEvent event) {
        if (tripRepository.existsInProgressTripByBoardPointId(event.boardPointId())) {
            throw new BoardPointInActiveTripException();
        }
    }
}
