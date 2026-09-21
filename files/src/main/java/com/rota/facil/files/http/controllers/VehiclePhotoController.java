package com.rota.facil.files.http.controllers;

import com.rota.facil.files.business.UploadVehiclePhotoUseCase;
import com.rota.facil.files.http.dto.response.FileResponse;
import com.rota.facil.users.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.UUID;

@RestController
@RequestMapping("/vehicles/{vehicleId}/photos")
@RequiredArgsConstructor
public class VehiclePhotoController {
    private final UploadVehiclePhotoUseCase uploadVehiclePhotoUseCase;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FileResponse> upload(@PathVariable UUID vehicleId,
                                                @RequestPart("file") MultipartFile file,
                                                @AuthenticationPrincipal UserEntity currentUser) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(uploadVehiclePhotoUseCase.execute(vehicleId, file, currentUser));
    }
}
