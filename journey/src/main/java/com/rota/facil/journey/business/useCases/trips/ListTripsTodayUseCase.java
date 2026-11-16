package com.rota.facil.journey.business.useCases.trips;

import com.rota.facil.annotations.UseCase;
import com.rota.facil.journey.http.dto.response.trips.TripResponse;
import com.rota.facil.journey.persistence.mappers.TripMapper;
import com.rota.facil.journey.persistence.repositories.TripRepository;
import com.rota.facil.users.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class ListTripsTodayUseCase {
    private final TripRepository tripRepository;
    private final TripMapper tripMapper;

    public List<TripResponse> execute(UserEntity currentUser) {
        return this.tripRepository.findAllTodayByPrefectureId(currentUser.getPrefecture().getId())
                .stream()
                .map(this.tripMapper::map)
                .toList();
    }
}
