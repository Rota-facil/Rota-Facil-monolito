package com.rota.facil.files.http.controllers;

import com.rota.facil.files.business.useCases.users.admins.EvaluateDocumentUseCase;
import com.rota.facil.files.business.useCases.users.drivers.FetchMyCNHUseCase;
import com.rota.facil.files.business.useCases.users.drivers.UploadCnhUseCase;
import com.rota.facil.files.business.useCases.users.students.FetchMyEnrollmentCertificateUseCase;
import com.rota.facil.files.business.useCases.users.students.UploadEnrollmentCertificateUseCase;
import com.rota.facil.files.domain.enums.FileValidationStatus;
import com.rota.facil.files.http.dto.response.FileWithValidationResponse;
import com.rota.facil.users.persistence.entities.UserEntity;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/documents")
public class DocumentsController {
    private final UploadEnrollmentCertificateUseCase uploadEnrollmentCertificateUseCase;
    private final UploadCnhUseCase uploadCnhUseCase;
    private final EvaluateDocumentUseCase evaluateDocumentUseCase;
    private final FetchMyEnrollmentCertificateUseCase fetchMyEnrollmentCertificateUseCase;
    private final FetchMyCNHUseCase fetchMyCNHUseCase;

    @PostMapping("/enrollment-certificate")
    public ResponseEntity<FileWithValidationResponse> uploadEnrollmentCertificate(
            @AuthenticationPrincipal UserEntity currentUser,
            @Parameter(required = true, description = "enrollment certificate")
            @RequestPart("file") MultipartFile file
    ) {
        return ResponseEntity.ok(this.uploadEnrollmentCertificateUseCase.execute(currentUser, file));
    }

    @GetMapping("/enrollment-certificate")
    public ResponseEntity<FileWithValidationResponse> fetchMyEnrollmentCertificate(
            @AuthenticationPrincipal UserEntity currentUser
    ) {
        return ResponseEntity.ok(this.fetchMyEnrollmentCertificateUseCase.execute(currentUser));
    }

    @PostMapping("/cnh")
    public ResponseEntity<FileWithValidationResponse> uploadCnh(
            @AuthenticationPrincipal UserEntity currentUser,
            @Parameter(required = true, description = "driver cnh")
            @RequestPart("file")
            MultipartFile file
    ) {
        return ResponseEntity.ok(this.uploadCnhUseCase.execute(currentUser, file));
    }

    @GetMapping("/cnh")
    public ResponseEntity<FileWithValidationResponse> fetchMyCNH(@AuthenticationPrincipal UserEntity currentUser) {
        return ResponseEntity.ok(this.fetchMyCNHUseCase.execute(currentUser));
    }

    @PatchMapping("/evaluate/{documentId}")
    public ResponseEntity<FileWithValidationResponse> evaluateDocument(
            @PathVariable UUID documentId,
            @RequestParam(name = "evaluation_status") FileValidationStatus fileValidationStatus,
            @AuthenticationPrincipal UserEntity currentUser
    ) {
        return ResponseEntity.ok(this.evaluateDocumentUseCase.execute(documentId, fileValidationStatus, currentUser));
    }
}
