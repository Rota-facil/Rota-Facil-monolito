package com.rota.facil.vehicles.business.helpers;

import com.rota.facil.users.domain.DriverStatus;
import com.rota.facil.users.exceptions.UserNotFoundExceptions;
import com.rota.facil.users.persistence.entities.UserEntity;
import com.rota.facil.users.persistence.repositories.UserRepository;
import com.rota.facil.vehicles.entities.VehicleEntity;
import com.rota.facil.vehicles.http.exceptions.DriverAlreadyHasVehicleException;
import com.rota.facil.vehicles.http.exceptions.DriverIsOnRouteException;
import com.rota.facil.vehicles.persistence.repositories.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FindDriverForVehicleUpdateHelper {
    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;

    public UserEntity execute(UUID driverId, UUID prefectureId, VehicleEntity vehicle) {
        if (driverId == null) return null;
        if (vehicle.getDriver() != null && driverId.equals(vehicle.getDriver().getId())) return vehicle.getDriver();

        UserEntity driver = userRepository.findByIdAndPrefectureId(driverId, prefectureId)
                .filter(UserEntity::isDriver)
                .orElseThrow(UserNotFoundExceptions::new);

        if (DriverStatus.ON_ROUTE.equals(driver.getStatus())) throw new DriverIsOnRouteException();

        vehicleRepository.findByDriverIdAndActiveTrue(driverId)
                .filter(currentVehicle -> !currentVehicle.getId().equals(vehicle.getId()))
                .ifPresent(currentVehicle -> { throw new DriverAlreadyHasVehicleException(); });

        return driver;
    }
}
