package com.rota.facil.files.business.institutions;

import com.rota.facil.annotations.UseCase;
import com.rota.facil.files.business.helpers.FindInstitutionPhotoByIdHelper;
import com.rota.facil.files.http.dto.response.FileResponse;
import com.rota.facil.files.http.mappers.FileResponseMapper;
import com.rota.facil.places.business.helpers.institutions.FindInstitutionByIdHelper;
import com.rota.facil.users.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class FetchInstitutionPhotoUseCase {
    private final FindInstitutionByIdHelper findInstitutionByIdHelper;
    private final FindInstitutionPhotoByIdHelper findInstitutionPhotoByIdHelper;
    private final FileResponseMapper fileResponseMapper;

    @Transactional(readOnly = true)
    public FileResponse execute(UUID institutionId, UUID photoId, UserEntity currentUser) {
        UUID prefectureId = currentUser.getPrefecture().getId();
        findInstitutionByIdHelper.execute(institutionId, prefectureId);
        return fileResponseMapper.map(findInstitutionPhotoByIdHelper.execute(photoId, institutionId, prefectureId));
    }
}
