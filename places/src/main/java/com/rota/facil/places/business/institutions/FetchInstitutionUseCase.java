package com.rota.facil.places.business.institutions;

import com.rota.facil.places.business.helpers.institutions.FindInstitutionByIdHelper;
import com.rota.facil.places.http.dto.response.institutions.InstitutionResponse;
import com.rota.facil.places.persistence.mappers.InstitutionMapper;
import com.rota.facil.users.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import com.rota.facil.annotations.UseCase;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class FetchInstitutionUseCase {
    private final FindInstitutionByIdHelper findInstitutionByIdHelper;
    private final InstitutionMapper institutionMapper;

    public InstitutionResponse execute(UUID institutionId, UserEntity currentUser) {
        return institutionMapper.mapToResponse(
                findInstitutionByIdHelper.execute(institutionId, currentUser.getPrefectureId())
        );
    }
}
