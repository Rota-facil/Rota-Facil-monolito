package com.rota.facil.files.business.useCases.institutions;

import com.rota.facil.annotations.UseCase;
import com.rota.facil.files.domain.FileCategory;
import com.rota.facil.files.http.dto.response.FileResponse;
import com.rota.facil.files.http.mappers.FileMapper;
import com.rota.facil.files.persistence.repositories.FileRepository;
import com.rota.facil.files.persistence.repositories.InstitutionFilesRepository;
import com.rota.facil.places.business.helpers.institutions.FindInstitutionByIdHelper;
import com.rota.facil.users.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class ListInstitutionPhotosUseCase {
    private final InstitutionFilesRepository institutionFilesRepository;
    private final FileMapper fileMapper;

    @Transactional(readOnly = true)
    public List<FileResponse> execute(UUID institutionId, UserEntity currentUser) {
        UUID prefectureId = currentUser.getPrefecture().getId();

        return this.institutionFilesRepository.findAllByInstitutionIdAndPrefectureIdAndFileCategory(
                        institutionId, prefectureId, FileCategory.PHOTO
                ).stream()
                .map(this.fileMapper::map)
                .toList();
    }
}
