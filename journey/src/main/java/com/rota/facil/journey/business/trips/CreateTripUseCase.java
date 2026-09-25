package com.rota.facil.journey.business.trips;

import com.rota.facil.journey.business.helpers.routes.FindRouteByIdHelper;
import com.rota.facil.journey.exceptions.CreateTripException;
import com.rota.facil.journey.http.dto.request.trips.CreateTripRequest;
import com.rota.facil.journey.http.dto.response.trips.TripResponse;
import com.rota.facil.journey.persistence.entities.RouteEntity;
import com.rota.facil.journey.persistence.entities.TripEntity;
import com.rota.facil.journey.persistence.mappers.TripMapper;
import com.rota.facil.journey.persistence.repositories.TripRepository;
import com.rota.facil.journey.persistence.repositories.RouteRecurringRepository;
import com.rota.facil.users.persistence.entities.UserEntity;
import com.rota.facil.vehicles.entities.VehicleEntity;
import lombok.RequiredArgsConstructor;
import com.rota.facil.annotations.UseCase;

@UseCase
@RequiredArgsConstructor
public class CreateTripUseCase {
    private final FindRouteByIdHelper findRouteByIdHelper;
    private final TripRepository tripRepository;
    private final RouteRecurringRepository routeRecurringRepository;
    private final TripMapper tripMapper;

    public TripResponse execute(UserEntity currentUser, CreateTripRequest request) {
        VehicleEntity validVehicleForTripFound = this.routeRecurringRepository.findVehicleByVehicleIdAndRouteId(request.vehicleId(), request.routeId())
                .orElseThrow(() -> new CreateTripException("Deve existir associação existente entre o veículo e a rota"));

        RouteEntity routeFound = this.findRouteByIdHelper.execute(request.routeId());

        TripEntity newTripAdded = this.tripRepository.save(
                TripEntity.builder()
                        .name(request.name())
                        .vehicle(validVehicleForTripFound)
                        .route(routeFound)
                        .prefectureId(currentUser.getPrefectureId())
                        .build()
        );

        return tripMapper.map(newTripAdded);
    }

}
