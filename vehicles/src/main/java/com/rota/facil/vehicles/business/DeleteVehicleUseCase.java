package com.rota.facil.vehicles.business;

import com.rota.facil.users.persistence.entities.UserEntity;
import com.rota.facil.vehicles.business.helpers.FindAnyVehicleByIdHelper;
import com.rota.facil.vehicles.entities.VehicleEntity;
import com.rota.facil.vehicles.persistence.repositories.VehicleRepository;
import com.rota.facil.vehicles.spring.events.dto.VehicleDeletedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteVehicleUseCase {
    private final FindAnyVehicleByIdHelper findAnyVehicleByIdHelper;
    private final VehicleRepository vehicleRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public void execute(UUID vehicleId, UserEntity currentUser) {
        VehicleEntity vehicle = findAnyVehicleByIdHelper.execute(vehicleId, currentUser.getPrefectureId());

        vehicle.deactivate();
        vehicleRepository.save(vehicle);
        eventPublisher.publishEvent(new VehicleDeletedEvent(vehicle.getId()));
    }
}
