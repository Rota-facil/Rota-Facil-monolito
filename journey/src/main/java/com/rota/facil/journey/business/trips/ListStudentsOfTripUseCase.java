package com.rota.facil.journey.business.trips;

import com.rota.facil.annotations.UseCase;
import com.rota.facil.journey.http.dto.response.trips.TripStudentsResponse;
import com.rota.facil.journey.persistence.mappers.TripUserMapper;
import com.rota.facil.journey.persistence.repositories.TripUserRepository;
import com.rota.facil.users.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class ListStudentsOfTripUseCase {
    private final TripUserRepository tripUserRepository;
    private final TripUserMapper tripUserMapper;

    public List<TripStudentsResponse> execute(UserEntity currentUser, UUID tripId) {
        return this.tripUserRepository.findAllByTripIdAndDriverIdAndPrefectureId(tripId, currentUser.getId(), currentUser.getPrefectureId())
                .stream()
                .map(tripUserMapper::mapTripStudents)
                .toList();

    }

}
