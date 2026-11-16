package com.rota.facil.files.business.useCases.users.drivers;

import com.rota.facil.annotations.UseCase;
import com.rota.facil.files.business.helpers.UploadCnhHelper;
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
    private final UploadCnhHelper uploadCnhHelper;
    private final FileMapper fileMapper;

    public FileWithValidationResponse execute(UserEntity currentUser, MultipartFile file) {
        return this.fileMapper.map(
                this.uploadCnhHelper.execute(currentUser, file)
        );
    }
}
