package com.rota.facil.files.business.useCases.users.drivers;

import com.rota.facil.annotations.UseCase;
import com.rota.facil.files.business.helpers.ValidatePDFHelper;
import com.rota.facil.files.domain.enums.FileCategory;
import com.rota.facil.files.http.dto.response.FileWithValidationResponse;
import com.rota.facil.files.http.mappers.FileMapper;
import com.rota.facil.files.persistence.entities.FileWithValidationEntity;
import com.rota.facil.files.persistence.repositories.FileWithValidationRepository;
import com.rota.facil.files.storage.FileStorage;
import com.rota.facil.users.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@UseCase
@RequiredArgsConstructor
public class UploadCnhUseCase {
    private final ValidatePDFHelper validatePDFHelper;
    private final FileStorage fileStorage;
    private final FileWithValidationRepository fileWithValidationRepository;
    private final FileMapper fileMapper;

    public FileWithValidationResponse execute(UserEntity currentUser, MultipartFile file) {
        this.validatePDFHelper.execute(file);

        FileWithValidationEntity cnh = this.fileWithValidationRepository.findCNHByUserIdAndFileCategory(currentUser.getId())
                .orElseGet(() ->
                        FileWithValidationEntity.newFile(
                                currentUser,
                                currentUser.getPrefecture(),
                                file.getOriginalFilename(),
                                file.getContentType(),
                                file.getSize(),
                                FileCategory.CNH
                        )
                );

        if (cnh.getId() != null) {
            this.fileStorage.delete(cnh.getObjectKey());
            cnh.update(file.getOriginalFilename(), file.getContentType(), file.getSize());
            cnh.generateObjectKey();
        }

        this.fileStorage.upload(file, cnh.getObjectKey());

        return this.fileMapper.map(
                this.fileWithValidationRepository.save(cnh)
        );
    }
}
