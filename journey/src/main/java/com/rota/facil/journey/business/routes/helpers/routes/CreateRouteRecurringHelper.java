package com.rota.facil.journey.business.routes.helpers.routes;

import com.rota.facil.journey.persistence.entities.RouteEntity;
import com.rota.facil.journey.persistence.entities.RouteRecurringEntity;
import com.rota.facil.journey.persistence.repositories.RouteRecurringRepository;
import com.rota.facil.vehicles.entities.VehicleEntity;
import com.rota.facil.vehicles.http.exceptions.VehicleNotFoundException;
import com.rota.facil.vehicles.persistence.repositories.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CreateRouteRecurringHelper {
    private final RouteRecurringRepository routeRecurringRepository;
    private final VehicleRepository vehicleRepository;

    public void execute(RouteEntity routeEntity, List<UUID> vehicles) {
        List<VehicleEntity> vehiclesFound = this.findAllVehicles(vehicles);

        List<RouteRecurringEntity> routeRecurringEntities = new ArrayList<>();

        for (VehicleEntity vehicle : vehiclesFound) {
            routeRecurringEntities.add(
                    RouteRecurringEntity.builder()
                            .route(routeEntity)
                            .vehicle(vehicle)
                            .build()
            );
        }

        this.routeRecurringRepository.saveAll(routeRecurringEntities);
    }

    private List<VehicleEntity> findAllVehicles(List<UUID> vehicles) {
        List<VehicleEntity> vehiclesFound = this.vehicleRepository.findAllById(vehicles);

        if (vehiclesFound.size() != vehicles.size()) throw new VehicleNotFoundException("Um dos veículos selecionado não foi encontrado");

        return vehiclesFound;
    }
}
