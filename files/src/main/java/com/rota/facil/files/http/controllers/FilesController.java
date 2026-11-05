package com.rota.facil.files.http.controllers;

import com.rota.facil.files.business.FetchVehiclePhotoUseCase;
import com.rota.facil.files.business.ListVehiclePhotosUseCase;
import com.rota.facil.files.business.UpdateVehiclePhotoUseCase;
import com.rota.facil.files.business.UploadVehiclePhotoUseCase;
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
@RequestMapping("/files")
@RequiredArgsConstructor
public class FilesController {
    private final UploadVehiclePhotoUseCase uploadVehiclePhotoUseCase;
    private final ListVehiclePhotosUseCase listVehiclePhotosUseCase;
    private final FetchVehiclePhotoUseCase fetchVehiclePhotoUseCase;
    private final UpdateVehiclePhotoUseCase updateVehiclePhotoUseCase;

    @PostMapping(value = "/vehicles/{vehicleId}/photos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FileResponse> uploadVehiclePhoto(@PathVariable UUID vehicleId,
                                               @RequestPart("file") MultipartFile file,
                                               @AuthenticationPrincipal UserEntity currentUser) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(uploadVehiclePhotoUseCase.execute(vehicleId, file, currentUser));
    }

    @GetMapping("/vehicles/{vehicleId}/photos")
    public ResponseEntity<List<FileResponse>> list(
            @PathVariable UUID vehicleId,
            @AuthenticationPrincipal UserEntity currentUser
    ) {
        return ResponseEntity.ok(listVehiclePhotosUseCase.execute(vehicleId, currentUser));
    }

    @GetMapping("/vehicles/{vehicleId}/photos/{photoId}")
    public ResponseEntity<FileResponse> fetch(
            @PathVariable UUID vehicleId,
            @PathVariable UUID photoId,
            @AuthenticationPrincipal UserEntity currentUser
    ) {
        return ResponseEntity.ok(fetchVehiclePhotoUseCase.execute(vehicleId, photoId, currentUser));
    }

    @PutMapping(value = "vehicles/{vehicleId}/photos/{photoId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FileResponse> update(
            @PathVariable UUID vehicleId,
            @PathVariable UUID photoId,
            @RequestPart("file") MultipartFile file,
            @AuthenticationPrincipal UserEntity currentUser
    ) {
        return ResponseEntity.ok(
                updateVehiclePhotoUseCase.execute(vehicleId, photoId, file, currentUser)
        );
    }
}
