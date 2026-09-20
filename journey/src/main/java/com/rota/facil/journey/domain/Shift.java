package com.rota.facil.journey.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Shift {
    MORNING("Manhã"),
    AFTERNOON("Tarde"),
    NIGHT("Noite");

    private final String title;
}
