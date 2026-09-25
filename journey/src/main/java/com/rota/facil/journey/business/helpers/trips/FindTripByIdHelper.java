package com.rota.facil.journey.business.helpers.trips;

import com.rota.facil.journey.exceptions.TripNotFoundException;
import com.rota.facil.journey.persistence.entities.TripEntity;
import com.rota.facil.journey.persistence.repositories.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FindTripByIdHelper {
    private final TripRepository tripRepository;

    public TripEntity execute(UUID tripId) {
        return this.tripRepository.findById(tripId)
                .orElseThrow(TripNotFoundException::new);
    }
}
