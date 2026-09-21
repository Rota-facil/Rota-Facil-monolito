package com.rota.facil.journey.spring.events;

import com.rota.facil.journey.persistence.repositories.RouteRecurringRepository;
import com.rota.facil.vehicles.spring.events.dto.VehicleDeletedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VehicleDeletedEventListener {
    private final RouteRecurringRepository routeRecurringRepository;

    @EventListener
    public void execute(VehicleDeletedEvent event) {
        routeRecurringRepository.deleteAllByVehicleId(event.vehicleId());
    }
}
