package com.rota.facil.files.business.useCases.users.students;

import com.rota.facil.annotations.UseCase;
import com.rota.facil.files.persistence.entities.DocumentEntity;
import com.rota.facil.files.persistence.repositories.DocumentRepository;
import com.rota.facil.files.persistence.repositories.FileRepository;
import com.rota.facil.users.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@UseCase
@RequiredArgsConstructor
public class UploadEnrollmentCertificateUseCase {
    private final DocumentRepository documentRepository;

    public void execute(UserEntity currentUser, MultipartFile file) {
//        DocumentEntity.newFile(
//
//        )
    }
}
