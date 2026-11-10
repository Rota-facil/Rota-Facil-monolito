package com.rota.facil.files.business.institutions;

import com.rota.facil.annotations.UseCase;
import com.rota.facil.files.business.helpers.ValidateImageHelper;
import com.rota.facil.files.domain.FileCategory;
import com.rota.facil.files.domain.OwnerType;
import com.rota.facil.files.http.dto.response.FileResponse;
import com.rota.facil.files.http.mappers.FileResponseMapper;
import com.rota.facil.files.persistence.entities.FileEntity;
import com.rota.facil.files.persistence.repositories.FileRepository;
import com.rota.facil.files.storage.FileStorage;
import com.rota.facil.places.business.helpers.institutions.FindInstitutionByIdHelper;
import com.rota.facil.places.persistence.entitites.InstitutionEntity;
import com.rota.facil.users.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class UploadInstitutionPhotoUseCase {
    private final FindInstitutionByIdHelper findInstitutionByIdHelper;
    private final ValidateImageHelper validateImageHelper;
    private final FileRepository fileRepository;
    private final FileStorage fileStorage;
    private final FileResponseMapper fileResponseMapper;


    public FileResponse execute(UUID institutionId, MultipartFile file, UserEntity currentUser) {
        InstitutionEntity institutionFound = this.findInstitutionByIdHelper.execute(institutionId, currentUser.getPrefecture().getId());

        this.validateImageHelper.execute(file);


        FileEntity saved = this.fileRepository.save(FileEntity.newFile(
                institutionFound.getId(), currentUser.getId(), currentUser.getPrefecture().getId(),
                file.getOriginalFilename(), file.getContentType(), file.getSize(),
                OwnerType.INSTITUTION, FileCategory.PHOTO
        ));

        this.fileStorage.upload(file, saved.getObjectKey());

        return this.fileResponseMapper.map(saved);
    }
}
