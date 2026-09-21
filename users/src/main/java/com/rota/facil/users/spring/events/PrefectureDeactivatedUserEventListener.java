package com.rota.facil.users.spring.events;

import com.rota.facil.prefectures.spring.events.dto.PrefectureDeactivatedEvent;
import com.rota.facil.users.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PrefectureDeactivatedUserEventListener {
    private final UserRepository userRepository;

    @EventListener
    public void execute(PrefectureDeactivatedEvent event) {
        userRepository.prepareDriversForDeactivation(event.prefectureId());
        userRepository.deactivateDriversAndAdminsByPrefectureId(event.prefectureId());
        userRepository.disassociateNonOperationalUsersByPrefectureId(event.prefectureId());
    }
}
