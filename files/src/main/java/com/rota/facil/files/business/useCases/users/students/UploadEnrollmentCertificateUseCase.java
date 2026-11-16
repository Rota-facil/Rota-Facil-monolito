package com.rota.facil.files.business.useCases.users.students;

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
public class UploadEnrollmentCertificateUseCase {
    private final FileStorage fileStorage;
    private final ValidatePDFHelper validatePDFHelper;
    private final FileWithValidationRepository fileWithValidationRepository;
    private final FileMapper fileMapper;

    public FileWithValidationResponse execute(UserEntity currentUser, MultipartFile file) {
        this.validatePDFHelper.execute(file);


        FileWithValidationEntity enrollmentCertificate = this.fileWithValidationRepository.findEnrollmentCertificateByUserId(currentUser.getId())
                .orElseGet(() ->
                        FileWithValidationEntity.newFile(
                                currentUser,
                                currentUser.getPrefecture(),
                                file.getOriginalFilename(),
                                file.getContentType(),
                                file.getSize(),
                                FileCategory.ENROLLMENT_CERTIFICATE
                        )
                );

        if (enrollmentCertificate.getId() != null) {
            this.fileStorage.delete(enrollmentCertificate.getObjectKey());
            enrollmentCertificate.update(file.getOriginalFilename(), file.getContentType(), file.getSize());

            enrollmentCertificate.generateObjectKey();
        }

        this.fileStorage.upload(file, enrollmentCertificate.getObjectKey());

        return this.fileMapper.map(
                this.fileWithValidationRepository.save(enrollmentCertificate)
        );
    }
}
