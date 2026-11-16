package com.rota.facil.journey.http.controllers;

import com.rota.facil.journey.business.useCases.metrics.FetchMetricsUseCase;
import com.rota.facil.journey.http.dto.response.metrics.MetricResponse;
import com.rota.facil.users.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/journey/metrics")
@RequiredArgsConstructor
public class MetricsController {
    private final FetchMetricsUseCase fetchMetricsUseCase;

    @GetMapping
    public MetricResponse fetch(@AuthenticationPrincipal UserEntity currentUser) {
        return fetchMetricsUseCase.execute(currentUser);
    }
}
