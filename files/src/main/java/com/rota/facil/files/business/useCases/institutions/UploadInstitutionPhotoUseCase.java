package com.rota.facil.files.business.useCases.institutions;

import com.rota.facil.annotations.UseCase;
import com.rota.facil.files.business.helpers.ValidateImageHelper;
import com.rota.facil.files.domain.enums.FileCategory;
import com.rota.facil.files.http.dto.response.FileResponse;
import com.rota.facil.files.http.mappers.FileMapper;
import com.rota.facil.files.persistence.entities.InstitutionFilesEntity;
import com.rota.facil.files.persistence.repositories.InstitutionFilesRepository;
import com.rota.facil.files.storage.FileStorage;
import com.rota.facil.places.business.helpers.institutions.FindInstitutionByIdHelper;
import com.rota.facil.places.persistence.entitites.InstitutionEntity;
import com.rota.facil.users.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class UploadInstitutionPhotoUseCase {
    private final FindInstitutionByIdHelper findInstitutionByIdHelper;
    private final ValidateImageHelper validateImageHelper;
    private final InstitutionFilesRepository institutionFilesRepository;
    private final FileStorage fileStorage;
    private final FileMapper fileMapper;


    @Transactional
    public FileResponse execute(UUID institutionId, MultipartFile file, UserEntity currentUser) {
        UUID prefectureId = currentUser.getPrefecture().getId();
        InstitutionEntity institutionFound = this.findInstitutionByIdHelper.execute(institutionId, prefectureId);

        this.validateImageHelper.execute(file);

        InstitutionFilesEntity institutionFileFound = this.institutionFilesRepository.findByInstitutionIdAndPrefectureIdAndFileCategory(institutionId, prefectureId, FileCategory.INSTITUTION_PHOTO)
                .orElseGet(() ->
                        InstitutionFilesEntity.newFile(
                                currentUser, currentUser.getPrefecture(),
                                file.getOriginalFilename(), file.getContentType(), file.getSize(),
                                FileCategory.INSTITUTION_PHOTO, institutionFound
                        )
                );

        if (institutionFileFound.getId() != null) {
            institutionFileFound.update(file.getOriginalFilename(), file.getContentType(), file.getSize());
            fileStorage.delete(institutionFileFound.getObjectKey());

            institutionFileFound.generateObjectKey();
        }

        InstitutionFilesEntity saved = this.institutionFilesRepository.save(institutionFileFound);

        this.fileStorage.upload(file, saved.getObjectKey());

        return this.fileMapper.map(saved);
    }
}
