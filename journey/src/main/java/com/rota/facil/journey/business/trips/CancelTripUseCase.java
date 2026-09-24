package com.rota.facil.journey.business.trips;

import com.rota.facil.journey.domain.Delay;
import com.rota.facil.journey.domain.Progress;
import com.rota.facil.journey.exceptions.TripAlreadyCancelledException;
import com.rota.facil.journey.exceptions.TripNotFoundException;
import com.rota.facil.journey.http.dto.request.trips.CancelTripRequest;
import com.rota.facil.journey.http.dto.response.trips.TripResponse;
import com.rota.facil.journey.persistence.entities.TripEntity;
import com.rota.facil.journey.persistence.entities.TripStatusEntity;
import com.rota.facil.journey.persistence.mappers.TripMapper;
import com.rota.facil.journey.persistence.repositories.TripRepository;
import com.rota.facil.users.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CancelTripUseCase {
    private final TripRepository tripRepository;
    private final TripMapper tripMapper;

    @Transactional
    public TripResponse execute(UserEntity currentUser, UUID tripId, CancelTripRequest request) {
        TripEntity trip = tripRepository.findTripByIdAndPrefectureIdAndDriverId(
                        tripId, currentUser.getPrefectureId(), currentUser.getId())
                .orElseThrow(TripNotFoundException::new);

        if (Progress.CANCELLED.equals(trip.getActualStatus())) {
            throw new TripAlreadyCancelledException();
        }

        TripStatusEntity status = TripStatusEntity.builder()
                .trip(trip)
                .delay(Delay.PUNCTUAL)
                .progress(Progress.CANCELLED)
                .description(Progress.CANCELLED.getTitle())
                .build();

        if (trip.getTripStatus() == null) trip.setTripStatus(new ArrayList<>());
        trip.getTripStatus().add(status);
        trip.setActualStatus(Progress.CANCELLED);
        trip.setReasonOfCancellation(request.reasonOfCancellation());
        trip.getVehicle().setStatus(com.rota.facil.vehicles.domain.VehicleStatus.OUT_OF_OPERATION);
        currentUser.moveToAvailable();

        return tripMapper.map(tripRepository.save(trip));
    }
}
