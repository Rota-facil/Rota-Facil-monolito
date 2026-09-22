package com.rota.facil.journey.business.routes;

import com.rota.facil.journey.domain.Presence;
import com.rota.facil.journey.http.dto.request.routes.UserLocationCheakinRequest;
import com.rota.facil.journey.http.exceptions.TripUserNotFoundExeption;
import com.rota.facil.journey.persistence.entities.TripEntity;
import com.rota.facil.journey.persistence.entities.TripUserEntity;
import com.rota.facil.journey.persistence.repositories.TripRepository;
import com.rota.facil.journey.persistence.repositories.TripUserRepository;
import com.rota.facil.users.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ValidateCheckinUseCase {
    private final TripUserRepository tripUserRepository;

    public void execute(UUID tripId, UserEntity currentUser, UserLocationCheakinRequest request) {
        TripUserEntity tripUser = this.tripUserRepository.findTripNotStartedByLatitudeAndLongitude(tripId, request.latitude(), request.longitude(), currentUser.getId())
                .orElseThrow(TripUserNotFoundExeption::new);

        tripUser.setPresence(Presence.CHECKIN);

        this.tripUserRepository.save(tripUser);
    }
}
