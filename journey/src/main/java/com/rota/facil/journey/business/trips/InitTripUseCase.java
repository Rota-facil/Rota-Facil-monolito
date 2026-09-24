package com.rota.facil.journey.business.trips;

import com.rota.facil.journey.domain.Delay;
import com.rota.facil.journey.domain.Progress;
import com.rota.facil.journey.exceptions.InvalidTimeToInitTripException;
import com.rota.facil.journey.exceptions.TripCannotBeStartedException;
import com.rota.facil.journey.exceptions.TripNotFoundException;
import com.rota.facil.journey.http.dto.response.trips.TripResponse;
import com.rota.facil.journey.persistence.entities.TripEntity;
import com.rota.facil.journey.persistence.entities.TripStatusEntity;
import com.rota.facil.journey.persistence.mappers.TripMapper;
import com.rota.facil.journey.persistence.repositories.TripRepository;
import com.rota.facil.journey.persistence.repositories.TripUserRepository;
import com.rota.facil.users.persistence.entities.UserEntity;
import com.rota.facil.users.persistence.repositories.UserRepository;
import com.rota.facil.vehicles.persistence.repositories.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InitTripUseCase {
    private final UserRepository userRepository;
    private final TripRepository tripRepository;
    private final VehicleRepository vehicleRepository;
    private final TripUserRepository tripUserRepository;
    private final TripMapper tripMapper;

    @Transactional
    public TripResponse execute(UserEntity currentUser, UUID tripId) {
        TripEntity trip = tripRepository.findTripByIdAndPrefectureIdAndDriverId(
                        tripId, currentUser.getPrefectureId(), currentUser.getId())
                .orElseThrow(TripNotFoundException::new);

        if (!Progress.NOT_STARTED.equals(trip.getActualStatus())) {
            throw new TripCannotBeStartedException("A ida só pode ser iniciada quando a viagem ainda não foi iniciada");
        }
        if (!tripUserRepository.existsByTripId(tripId)) {
            throw new TripCannotBeStartedException("Não é possível iniciar a ida sem alunos cadastrados na viagem");
        }

        Delay delay = calculateDelay(trip);
        TripStatusEntity status = TripStatusEntity.builder()
                .trip(trip)
                .delay(delay)
                .progress(Progress.STARTED)
                .description(Progress.STARTED.getTitle())
                .build();

        if (trip.getTripStatus() == null) trip.setTripStatus(new ArrayList<>());

        trip.getTripStatus().add(status);
        trip.setActualStatus(Progress.STARTED);
        trip.getVehicle().setStatus(com.rota.facil.vehicles.domain.VehicleStatus.OPERATION);

        currentUser.moveToOnRoute();
        currentUser.increaseTrips();

        this.vehicleRepository.save(trip.getVehicle());
        this.userRepository.save(currentUser);
        return tripMapper.map(tripRepository.save(trip));
    }

    private Delay calculateDelay(TripEntity trip) {
        LocalTime now = LocalTime.now();
        LocalTime start = trip.getRoute().getGoing();
        LocalTime finish = trip.getRoute().getGoingFinish();

        if (now.equals(start)) return Delay.PUNCTUAL;
        if (now.isAfter(start) && now.isBefore(finish)) return Delay.LATE;
        if (now.isBefore(start) && now.isAfter(start.minusMinutes(6))) return Delay.EARLY;

        throw new InvalidTimeToInitTripException("Você só pode iniciar uma viagem com 6 minutos adiantados ou antes do início da volta");
    }
}
