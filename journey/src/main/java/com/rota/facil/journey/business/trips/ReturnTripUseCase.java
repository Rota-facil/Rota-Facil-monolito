package com.rota.facil.journey.business.trips;

import com.rota.facil.journey.http.dto.response.trips.TripResponse;
import com.rota.facil.users.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import com.rota.facil.annotations.UseCase;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class ReturnTripUseCase {
    public TripResponse execute(UserEntity currentUser, UUID tripId) {
        return null;
    }
}
