package com.rota.facil.files.http.controllers;

import com.rota.facil.files.business.boardPoints.FetchBoardPointPhotoUseCase;
import com.rota.facil.files.business.boardPoints.ListBoardPointPhotosUseCase;
import com.rota.facil.files.business.boardPoints.UpdateBoardPointPhotoUseCase;
import com.rota.facil.files.business.boardPoints.UploadBoardPointPhotoUseCase;
import com.rota.facil.files.business.institutions.UploadInstitutionPhotoUseCase;
import com.rota.facil.files.business.institutions.FetchInstitutionPhotoUseCase;
import com.rota.facil.files.business.institutions.ListInstitutionPhotosUseCase;
import com.rota.facil.files.business.institutions.UpdateInstitutionPhotoUseCase;
import com.rota.facil.files.business.vehicles.FetchVehiclePhotoUseCase;
import com.rota.facil.files.business.vehicles.ListVehiclePhotosUseCase;
import com.rota.facil.files.business.vehicles.UpdateVehiclePhotoUseCase;
import com.rota.facil.files.business.vehicles.UploadVehiclePhotoUseCase;
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
@RequestMapping(value = "/files")
@RequiredArgsConstructor
public class FilesController {
    private final UploadVehiclePhotoUseCase uploadVehiclePhotoUseCase;
    private final ListVehiclePhotosUseCase listVehiclePhotosUseCase;
    private final FetchVehiclePhotoUseCase fetchVehiclePhotoUseCase;
    private final UpdateVehiclePhotoUseCase updateVehiclePhotoUseCase;

    private final UploadInstitutionPhotoUseCase uploadInstitutionPhotoUseCase;
    private final ListInstitutionPhotosUseCase listInstitutionPhotosUseCase;
    private final FetchInstitutionPhotoUseCase fetchInstitutionPhotoUseCase;
    private final UpdateInstitutionPhotoUseCase updateInstitutionPhotoUseCase;

    private final UploadBoardPointPhotoUseCase uploadBoardPointPhotoUseCase;
    private final ListBoardPointPhotosUseCase listBoardPointPhotosUseCase;
    private final FetchBoardPointPhotoUseCase fetchBoardPointPhotoUseCase;
    private final UpdateBoardPointPhotoUseCase updateBoardPointPhotoUseCase;

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

    @PostMapping(value = "/institutions/{institutionId}/photos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FileResponse> uploadInstitutionPhoto(
            @PathVariable UUID institutionId,
            @RequestPart("file") MultipartFile file,
            @AuthenticationPrincipal UserEntity currentUser
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.uploadInstitutionPhotoUseCase.execute(institutionId, file, currentUser));
    }

    @GetMapping("/institutions/{institutionId}/photos")
    public ResponseEntity<List<FileResponse>> listInstitutionPhotos(
            @PathVariable UUID institutionId,
            @AuthenticationPrincipal UserEntity currentUser
    ) {
        return ResponseEntity.ok(listInstitutionPhotosUseCase.execute(institutionId, currentUser));
    }

    @GetMapping("/institutions/{institutionId}/photos/{photoId}")
    public ResponseEntity<FileResponse> fetchInstitutionPhoto(
            @PathVariable UUID institutionId,
            @PathVariable UUID photoId,
            @AuthenticationPrincipal UserEntity currentUser
    ) {
        return ResponseEntity.ok(fetchInstitutionPhotoUseCase.execute(institutionId, photoId, currentUser));
    }

    @PutMapping(value = "/institutions/{institutionId}/photos/{photoId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FileResponse> updateInstitutionPhoto(
            @PathVariable UUID institutionId,
            @PathVariable UUID photoId,
            @RequestPart("file") MultipartFile file,
            @AuthenticationPrincipal UserEntity currentUser
    ) {
        return ResponseEntity.ok(updateInstitutionPhotoUseCase.execute(institutionId, photoId, file, currentUser));
    }

    @PostMapping(value = "/board-points/{boardPointId}/photos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FileResponse> uploadBoardPointPhoto(
            @PathVariable UUID boardPointId,
            @RequestPart("file") MultipartFile file,
            @AuthenticationPrincipal UserEntity currentUser
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(uploadBoardPointPhotoUseCase.execute(boardPointId, file, currentUser));
    }

    @GetMapping("/board-points/{boardPointId}/photos")
    public ResponseEntity<List<FileResponse>> listBoardPointPhotos(
            @PathVariable UUID boardPointId,
            @AuthenticationPrincipal UserEntity currentUser
    ) {
        return ResponseEntity.ok(listBoardPointPhotosUseCase.execute(boardPointId, currentUser));
    }

    @GetMapping("/board-points/{boardPointId}/photos/{photoId}")
    public ResponseEntity<FileResponse> fetchBoardPointPhoto(
            @PathVariable UUID boardPointId,
            @PathVariable UUID photoId,
            @AuthenticationPrincipal UserEntity currentUser
    ) {
        return ResponseEntity.ok(fetchBoardPointPhotoUseCase.execute(boardPointId, photoId, currentUser));
    }

    @PutMapping(value = "/board-points/{boardPointId}/photos/{photoId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FileResponse> updateBoardPointPhoto(
            @PathVariable UUID boardPointId,
            @PathVariable UUID photoId,
            @RequestPart("file") MultipartFile file,
            @AuthenticationPrincipal UserEntity currentUser
    ) {
        return ResponseEntity.ok(updateBoardPointPhotoUseCase.execute(boardPointId, photoId, file, currentUser));
    }
}
