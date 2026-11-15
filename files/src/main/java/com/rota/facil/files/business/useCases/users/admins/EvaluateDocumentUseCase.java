package com.rota.facil.files.business.useCases.users.admins;

import com.rota.facil.annotations.UseCase;
import com.rota.facil.files.domain.enums.FileValidationStatus;
import com.rota.facil.files.http.dto.response.FileWithValidationResponse;
import com.rota.facil.files.http.exceptions.FileNotFoundException;
import com.rota.facil.files.http.mappers.FileMapper;
import com.rota.facil.files.persistence.entities.FileWithValidationEntity;
import com.rota.facil.files.persistence.repositories.FileWithValidationRepository;
import com.rota.facil.users.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class EvaluateDocumentUseCase {
    private final FileWithValidationRepository fileWithValidationRepository;
    private final FileMapper fileMapper;

    public FileWithValidationResponse execute(UUID documentId, FileValidationStatus fileValidationStatus, UserEntity currentUser) {
        FileWithValidationEntity fileFound = this.fileWithValidationRepository.findByIdAndPrefectureId(documentId, currentUser.getPrefecture().getId())
                .orElseThrow(FileNotFoundException::new);

        fileFound.setValidationStatus(fileValidationStatus);

        return this.fileMapper.map(
                this.fileWithValidationRepository.save(fileFound)
        );
    }
}
