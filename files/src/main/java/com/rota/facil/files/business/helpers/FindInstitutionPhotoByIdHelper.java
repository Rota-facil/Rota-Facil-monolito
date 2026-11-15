package com.rota.facil.files.business.helpers;

import com.rota.facil.files.domain.FileCategory;
import com.rota.facil.files.http.exceptions.FileNotFoundException;
import com.rota.facil.files.persistence.entities.FileEntity;
import com.rota.facil.files.persistence.repositories.FileRepository;
import com.rota.facil.files.persistence.repositories.InstitutionFilesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FindInstitutionPhotoByIdHelper {
    private final InstitutionFilesRepository institutionFilesRepository;

    public FileEntity execute(UUID photoId, UUID institutionId, UUID prefectureId) {
        return this.institutionFilesRepository.findByIdAndInstitutionIdAndPrefectureAndFileCategory(
                        photoId, institutionId, prefectureId, FileCategory.PHOTO
                )
                .orElseThrow(() -> new FileNotFoundException("Foto da instituição não foi encontrada"));
    }
}
