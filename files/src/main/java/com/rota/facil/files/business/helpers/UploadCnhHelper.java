package com.rota.facil.files.business.helpers;

import com.rota.facil.files.domain.enums.FileCategory;
import com.rota.facil.files.persistence.entities.FileWithValidationEntity;
import com.rota.facil.files.persistence.repositories.FileWithValidationRepository;
import com.rota.facil.files.storage.FileStorage;
import com.rota.facil.users.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class UploadCnhHelper {
    private final ValidatePDFHelper validatePDFHelper;
    private final FileStorage fileStorage;
    private final FileWithValidationRepository fileWithValidationRepository;

    public FileWithValidationEntity execute(UserEntity currentUser, MultipartFile file) {
        this.validatePDFHelper.execute(file);

        FileWithValidationEntity cnh = this.fileWithValidationRepository.findCNHByUserIdAndPrefectureId(currentUser.getId(), currentUser.getPrefecture().getId())
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

        return this.fileWithValidationRepository.save(cnh);
    }
}
