package com.rota.facil.users.domain.intermodule.ports.journey;

import java.util.UUID;

public interface VerifyUserInSomeStartedTripPort {
    boolean execute(UUID userId);
}
