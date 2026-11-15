package com.rota.facil.files.http.controllers;

import com.rota.facil.files.business.useCases.users.students.UploadEnrollmentCertificateUseCase;
import com.rota.facil.files.http.dto.response.FileResponse;
import com.rota.facil.files.http.dto.response.FileWithValidationResponse;
import com.rota.facil.users.persistence.entities.UserEntity;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/documents")
public class DocumentsController {
    private final UploadEnrollmentCertificateUseCase uploadEnrollmentCertificateUseCase;

    @PostMapping("/enrollment-certificate")
    public ResponseEntity<FileWithValidationResponse> uploadEnrollmentCertificate(
            @AuthenticationPrincipal UserEntity currentUser,
            @Parameter(required = true, description = "enrollment certificate")
            @RequestPart("file") MultipartFile file
    ) {
        return ResponseEntity.ok(this.uploadEnrollmentCertificateUseCase.execute(currentUser, file));
    }
}
