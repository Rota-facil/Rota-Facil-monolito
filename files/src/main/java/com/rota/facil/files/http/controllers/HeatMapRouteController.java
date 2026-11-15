package com.rota.facil.files.http.controllers;

import com.rota.facil.files.business.useCases.routes.UploadHeatMapRouteUseCase;
import com.rota.facil.users.persistence.entities.UserEntity;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.UUID;

@RestController
@RequestMapping("/files/heat-map")
@RequiredArgsConstructor
public class HeatMapRouteController {
    private final UploadHeatMapRouteUseCase uploadHeatMapRouteUseCase;

    @PostMapping(value = "/{routeId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> upload(
            @PathVariable UUID routeId,
            @RequestPart("file") MultipartFile file,
            @AuthenticationPrincipal UserEntity currentUser
    ) {
        uploadHeatMapRouteUseCase.execute(routeId, file, currentUser); return ResponseEntity.ok().build();
    }
}
