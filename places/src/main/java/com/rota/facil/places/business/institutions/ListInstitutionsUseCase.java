package com.rota.facil.places.business.institutions;

import com.rota.facil.places.http.dto.response.institutions.InstitutionResponse;
import com.rota.facil.places.persistence.mappers.InstitutionMapper;
import com.rota.facil.places.persistence.repositories.InstitutionRepository;
import com.rota.facil.users.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.rota.facil.annotations.UseCase;

@UseCase
@RequiredArgsConstructor
public class ListInstitutionsUseCase {
    private final InstitutionRepository institutionRepository;
    private final InstitutionMapper institutionMapper;

    public Page<InstitutionResponse> execute(Pageable pageable, UserEntity currentUser) {
        return institutionRepository
                .findAllByPrefectureIdAndActiveTrue(currentUser.getPrefectureId(), pageable)
                .map(institutionMapper::mapToResponse);
    }
}
