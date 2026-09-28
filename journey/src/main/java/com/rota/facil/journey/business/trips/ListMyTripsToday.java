package com.rota.facil.journey.business.trips;

import com.rota.facil.annotations.UseCase;
import com.rota.facil.journey.http.dto.response.trips.TripResponse;
import com.rota.facil.journey.persistence.entities.TripEntity;
import com.rota.facil.journey.persistence.mappers.TripMapper;
import com.rota.facil.journey.persistence.repositories.TripRepository;
import com.rota.facil.journey.persistence.repositories.TripUserRepository;
import com.rota.facil.users.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class ListMyTripsToday {
    private final TripUserRepository tripUserRepository;
    private final TripRepository tripRepository;
    private final TripMapper tripMapper;

    public List<TripResponse> execute(UserEntity currentUser) {
            List<TripEntity> trips;

            switch (currentUser.getRole()) {
                case DRIVER -> trips = this.tripRepository.findALLByDriverIdAndPrefectureId(currentUser.getId(), currentUser.getPrefectureId());
                case STUDENT -> trips = this.tripUserRepository.findAllTripsByStudentIdAndPrefectureId(currentUser.getId(), currentUser.getPrefectureId());
                default -> throw new RuntimeException();
            }

            return trips.stream().map(this.tripMapper::map).toList();
    }
}
