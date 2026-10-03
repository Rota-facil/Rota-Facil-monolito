package com.rota.facil.journey.business.helpers.routes;

import com.rota.facil.journey.exceptions.UpdateRouteException;
import com.rota.facil.journey.persistence.repositories.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ValidateUpdateRouteHelper {
    private final TripRepository tripRepository;

    public void execute(UUID routeId) {
        if (this.tripRepository.countTripsStartedByRouteId(routeId) > 0) {
            throw new UpdateRouteException(
                    "Não é possível atualizar a rota porque ainda existem viagens em curso nesse momento"
            );
        }
    }
}
