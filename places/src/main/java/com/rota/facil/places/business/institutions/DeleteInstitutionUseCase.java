package com.rota.facil.places.business.institutions;

import com.rota.facil.places.business.helpers.institutions.FindInstitutionByIdHelper;
import com.rota.facil.places.persistence.entitites.InstitutionEntity;
import com.rota.facil.places.persistence.repositories.InstitutionRepository;
import com.rota.facil.places.spring.events.dto.ValidateInstitutionDeactivationEvent;
import com.rota.facil.users.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteInstitutionUseCase {
    private final FindInstitutionByIdHelper findInstitutionByIdHelper;
    private final InstitutionRepository institutionRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public void execute(UUID institutionId, UserEntity currentUser) {
        InstitutionEntity institution = findInstitutionByIdHelper.execute(
                institutionId,
                currentUser.getPrefectureId()
        );

        eventPublisher.publishEvent(new ValidateInstitutionDeactivationEvent(institutionId));

        institution.deactivate();
        institutionRepository.save(institution);
    }
}
