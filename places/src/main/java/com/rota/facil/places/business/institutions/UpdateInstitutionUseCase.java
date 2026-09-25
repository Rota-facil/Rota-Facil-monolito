package com.rota.facil.places.business.institutions;

import com.rota.facil.places.business.helpers.institutions.FindInstitutionByIdHelper;
import com.rota.facil.places.domain.UpdateInstitutionData;
import com.rota.facil.places.http.dto.request.institutions.UpdateInstitutionRequest;
import com.rota.facil.places.http.dto.response.institutions.InstitutionResponse;
import com.rota.facil.places.persistence.entitites.InstitutionEntity;
import com.rota.facil.places.persistence.mappers.InstitutionMapper;
import com.rota.facil.places.persistence.repositories.InstitutionRepository;
import com.rota.facil.users.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import com.rota.facil.annotations.UseCase;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class UpdateInstitutionUseCase {
    private final FindInstitutionByIdHelper findInstitutionByIdHelper;
    private final InstitutionRepository institutionRepository;
    private final InstitutionMapper institutionMapper;

    @Transactional
    public InstitutionResponse execute(UUID institutionId, UpdateInstitutionRequest request, UserEntity currentUser) {
        InstitutionEntity institution = findInstitutionByIdHelper.execute(
                institutionId,
                currentUser.getPrefectureId()
        );
        UpdateInstitutionData updateData = institutionMapper.map(request);

        institution.update(updateData);

        return institutionMapper.mapToResponse(institutionRepository.save(institution));
    }
}
