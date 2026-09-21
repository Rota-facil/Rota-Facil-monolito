package com.rota.facil.journey.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Delay {
    LATE("Atrasado"),
    PUNCTUAL("Pontual"),
    EARLY("Adiantado");

    private final String title;
}
