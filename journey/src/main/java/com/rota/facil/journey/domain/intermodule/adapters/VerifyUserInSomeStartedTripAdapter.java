package com.rota.facil.journey.domain.intermodule.adapters;

import com.rota.facil.annotations.UseCase;
import com.rota.facil.journey.persistence.repositories.TripUserRepository;
import com.rota.facil.users.domain.intermodule.ports.journey.VerifyUserInSomeStartedTripPort;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class VerifyUserInSomeStartedTripAdapter implements VerifyUserInSomeStartedTripPort {
    private final TripUserRepository tripUserRepository;

    @Override
    public boolean execute(UUID userId) {
        return this.tripUserRepository.findStartedByUserId(userId)
                .isPresent();
    }
}
