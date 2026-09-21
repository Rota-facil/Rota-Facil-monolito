package com.rota.facil.journey.spring.events;

import com.rota.facil.journey.persistence.repositories.RouteRepository;
import com.rota.facil.journey.persistence.repositories.TripRepository;
import com.rota.facil.prefectures.spring.events.dto.PrefectureDeactivatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PrefectureDeactivatedJourneyEventListener {
    private final RouteRepository routeRepository;
    private final TripRepository tripRepository;

    @EventListener
    public void execute(PrefectureDeactivatedEvent event) {
        routeRepository.deactivateAllByPrefectureId(event.prefectureId());
        tripRepository.cancelAllPendingByPrefectureId(
                event.prefectureId(),
                "Viagem cancelada devido à desativação da prefeitura"
        );
    }
}
