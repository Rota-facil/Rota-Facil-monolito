package com.rota.facil.vehicles.business.useCases.vehicles;

import com.rota.facil.annotations.UseCase;
import com.rota.facil.users.http.exceptions.UserNotFoundExceptions;
import com.rota.facil.users.persistence.entities.UserEntity;
import com.rota.facil.users.persistence.repositories.UserRepository;
import com.rota.facil.vehicles.http.dto.request.vehicles.ChangeDriverOfVehicleRequest;
import com.rota.facil.vehicles.http.dto.response.vehicles.VehicleResponse;
import com.rota.facil.vehicles.http.exceptions.ChangeDriverOfVehicleException;
import com.rota.facil.vehicles.http.exceptions.VehicleNotFoundException;
import com.rota.facil.vehicles.persistence.entities.VehicleEntity;
import com.rota.facil.vehicles.persistence.mappers.VehicleMapper;
import com.rota.facil.vehicles.persistence.repositories.VehicleRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class ChangeDriverOfVehicleUseCase {
    private final UserRepository userRepository;
    private final VehicleMapper vehicleMapper;
    private final VehicleRepository vehicleRepository;

    public VehicleResponse execute(UserEntity currentUser, UUID vehicleId, ChangeDriverOfVehicleRequest request) {
        VehicleEntity vehicleFound = this.vehicleRepository.findByIdAndPrefectureId(vehicleId, currentUser.getPrefecture().getId())
                .orElseThrow(VehicleNotFoundException::new);

        if (vehicleFound.inOperation()) throw new ChangeDriverOfVehicleException("O veículo selecionado precisa estar fora de operação para trocar o motorista");

        if (request.driverId() == null || request.driverId().equals(vehicleFound.getDriver().getId())) {

            vehicleFound.setDriver(null);
            return this.vehicleMapper.mapResponse(
                    this.vehicleRepository.save(vehicleFound)
            );
        }

        UserEntity driverFound = this.userRepository.findDriverByIdAndPrefectureId(request.driverId(), currentUser.getPrefecture().getId())
                .orElseThrow(UserNotFoundExceptions::new);

        Optional<VehicleEntity> anotherVehicleOfNewDriverExisting = this.vehicleRepository.findByDriverIdAndPrefectureId(driverFound.getId(), driverFound.getPrefecture().getId());

        if (anotherVehicleOfNewDriverExisting.isEmpty()) {
            vehicleFound.setDriver(driverFound);

            return this.vehicleMapper.mapResponse(
                    this.vehicleRepository.save(vehicleFound)
            );
        }

        VehicleEntity anotherVehicleOfNewDriverFound = anotherVehicleOfNewDriverExisting.get();

        if (anotherVehicleOfNewDriverFound.inOperation()) throw new ChangeDriverOfVehicleException("O motorista escolhido está em rota nesse momento");

        anotherVehicleOfNewDriverFound.setDriver(null);
        vehicleFound.setDriver(driverFound);

        this.vehicleRepository.save(anotherVehicleOfNewDriverFound);
        return this.vehicleMapper.mapResponse(
                this.vehicleRepository.save(vehicleFound)
        );
    }
}
