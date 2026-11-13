package com.rota.facil.journey.spring.events;

import com.rota.facil.journey.http.exceptions.InstitutionInActiveTripException;
import com.rota.facil.journey.persistence.repositories.TripRepository;
import com.rota.facil.places.spring.events.dto.ValidateInstitutionDeactivationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidateInstitutionDeactivationEventListener {
    private final TripRepository tripRepository;

    @EventListener
    public void execute(ValidateInstitutionDeactivationEvent event) {
        if (tripRepository.existsInProgressTripByInstitutionId(event.institutionId())) {
            throw new InstitutionInActiveTripException();
        }
    }
}
