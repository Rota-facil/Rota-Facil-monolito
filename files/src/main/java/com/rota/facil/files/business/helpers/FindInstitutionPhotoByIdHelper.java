package com.rota.facil.files.business.helpers;

import com.rota.facil.files.domain.FileCategory;
import com.rota.facil.files.http.exceptions.FileNotFoundException;
import com.rota.facil.files.persistence.entities.FileEntity;
import com.rota.facil.files.persistence.repositories.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FindInstitutionPhotoByIdHelper {
    private final FileRepository fileRepository;

    public FileEntity execute(UUID photoId, UUID institutionId, UUID prefectureId) {
        return fileRepository.findByIdAndOwnerIdAndPrefectureIdAndFileCategory(
                        photoId, institutionId, prefectureId, FileCategory.PHOTO
                )
                .filter(file -> file.getOwnerType().name().equals("INSTITUTION"))
                .orElseThrow(() -> new FileNotFoundException("Foto da instituição não foi encontrada"));
    }
}
