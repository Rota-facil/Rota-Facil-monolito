package com.rota.facil.files.http.controllers;

import com.rota.facil.files.business.useCases.institutions.FetchInstitutionPhotoUseCase;
import com.rota.facil.files.business.useCases.institutions.ListInstitutionPhotosUseCase;
import com.rota.facil.files.business.useCases.institutions.UpdateInstitutionPhotoUseCase;
import com.rota.facil.files.business.useCases.institutions.UploadInstitutionPhotoUseCase;
import com.rota.facil.files.http.dto.response.FileResponse;
import com.rota.facil.users.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/files/institutions")
@RequiredArgsConstructor
public class InstitutionsPhotoController {
    private final UploadInstitutionPhotoUseCase uploadInstitutionPhotoUseCase;
    private final ListInstitutionPhotosUseCase listInstitutionPhotosUseCase;
    private final FetchInstitutionPhotoUseCase fetchInstitutionPhotoUseCase;
    private final UpdateInstitutionPhotoUseCase updateInstitutionPhotoUseCase;

    @PostMapping(value = "/{institutionId}/photos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FileResponse> upload(@PathVariable UUID institutionId, @RequestPart("file") MultipartFile file, @AuthenticationPrincipal UserEntity currentUser) {
        return ResponseEntity.status(HttpStatus.CREATED).body(uploadInstitutionPhotoUseCase.execute(institutionId, file, currentUser));
    }

    @GetMapping("/{institutionId}/photos")
    public ResponseEntity<List<FileResponse>> list(@PathVariable UUID institutionId, @AuthenticationPrincipal UserEntity currentUser) {
        return ResponseEntity.ok(listInstitutionPhotosUseCase.execute(institutionId, currentUser));
    }

    @GetMapping("/{institutionId}/photos/{photoId}")
    public ResponseEntity<FileResponse> fetch(@PathVariable UUID institutionId, @PathVariable UUID photoId, @AuthenticationPrincipal UserEntity currentUser) {
        return ResponseEntity.ok(fetchInstitutionPhotoUseCase.execute(institutionId, photoId, currentUser));
    }

    @PutMapping(value = "/{institutionId}/photos/{photoId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FileResponse> update(@PathVariable UUID institutionId, @PathVariable UUID photoId, @RequestPart("file") MultipartFile file, @AuthenticationPrincipal UserEntity currentUser) {
        return ResponseEntity.ok(updateInstitutionPhotoUseCase.execute(institutionId, photoId, file, currentUser));
    }
}
