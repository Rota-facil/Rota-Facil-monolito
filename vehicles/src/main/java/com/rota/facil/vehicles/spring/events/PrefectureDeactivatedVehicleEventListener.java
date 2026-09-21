package com.rota.facil.vehicles.spring.events;

import com.rota.facil.prefectures.spring.events.dto.PrefectureDeactivatedEvent;
import com.rota.facil.vehicles.persistence.repositories.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PrefectureDeactivatedVehicleEventListener {
    private final VehicleRepository vehicleRepository;

    @EventListener
    public void execute(PrefectureDeactivatedEvent event) {
        vehicleRepository.deactivateAllByPrefectureId(event.prefectureId());
    }
}
