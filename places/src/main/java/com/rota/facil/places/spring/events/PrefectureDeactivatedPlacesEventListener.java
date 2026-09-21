package com.rota.facil.places.spring.events;

import com.rota.facil.places.persistence.repositories.BoardPointRepository;
import com.rota.facil.places.persistence.repositories.InstitutionRepository;
import com.rota.facil.prefectures.spring.events.dto.PrefectureDeactivatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PrefectureDeactivatedPlacesEventListener {
    private final InstitutionRepository institutionRepository;
    private final BoardPointRepository boardPointRepository;

    @EventListener
    public void execute(PrefectureDeactivatedEvent event) {
        institutionRepository.deactivateAllByPrefectureId(event.prefectureId());
        boardPointRepository.deactivateAllByPrefectureId(event.prefectureId());
    }
}
