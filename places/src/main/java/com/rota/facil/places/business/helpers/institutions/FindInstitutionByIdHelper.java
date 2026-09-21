package com.rota.facil.places.business.helpers.institutions;

import com.rota.facil.places.http.exceptions.InstitutionNotFoundException;
import com.rota.facil.places.persistence.entitites.InstitutionEntity;
import com.rota.facil.places.persistence.repositories.InstitutionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FindInstitutionByIdHelper {
    private final InstitutionRepository institutionRepository;

    public InstitutionEntity execute(UUID institutionId, UUID prefectureId) {
        return institutionRepository.findByIdAndPrefectureIdAndActiveTrue(institutionId, prefectureId)
                .orElseThrow(() -> new InstitutionNotFoundException("Instituição não foi encontrada"));
    }
}
