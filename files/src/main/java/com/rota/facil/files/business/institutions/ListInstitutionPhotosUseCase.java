package com.rota.facil.files.business.institutions;

import com.rota.facil.annotations.UseCase;
import com.rota.facil.files.domain.FileCategory;
import com.rota.facil.files.http.dto.response.FileResponse;
import com.rota.facil.files.http.mappers.FileResponseMapper;
import com.rota.facil.files.persistence.repositories.FileRepository;
import com.rota.facil.places.business.helpers.institutions.FindInstitutionByIdHelper;
import com.rota.facil.users.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class ListInstitutionPhotosUseCase {
    private final FindInstitutionByIdHelper findInstitutionByIdHelper;
    private final FileRepository fileRepository;
    private final FileResponseMapper fileResponseMapper;

    @Transactional(readOnly = true)
    public List<FileResponse> execute(UUID institutionId, UserEntity currentUser) {
        UUID prefectureId = currentUser.getPrefecture().getId();
        findInstitutionByIdHelper.execute(institutionId, prefectureId);
        return fileRepository.findAllByOwnerIdAndPrefectureIdAndFileCategoryOrderByCreatedAtDesc(
                        institutionId, prefectureId, FileCategory.PHOTO
                ).stream()
                .filter(file -> file.getOwnerType().name().equals("INSTITUTION"))
                .map(fileResponseMapper::map)
                .toList();
    }
}
