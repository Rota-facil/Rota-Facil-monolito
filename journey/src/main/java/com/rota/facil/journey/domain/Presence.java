package com.rota.facil.journey.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Presence {
    CHECKIN("Check-in"),
    PENDING("Pendente"),
    ABSENT("Ausente");

    private final String description;
}
