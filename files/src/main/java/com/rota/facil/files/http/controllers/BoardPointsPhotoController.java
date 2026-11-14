package com.rota.facil.files.http.controllers;

import com.rota.facil.files.business.useCases.boardPoints.*;
import com.rota.facil.files.http.dto.response.FileResponse;
import com.rota.facil.users.persistence.entities.UserEntity;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/files/board-points")
@RequiredArgsConstructor
public class BoardPointsPhotoController {
    private final UploadBoardPointPhotoUseCase uploadBoardPointPhotoUseCase;
    private final ListBoardPointPhotosUseCase listBoardPointPhotosUseCase;
    private final FetchBoardPointPhotoUseCase fetchBoardPointPhotoUseCase;
    private final UpdateBoardPointPhotoUseCase updateBoardPointPhotoUseCase;

    @PostMapping(value = "/{boardPointId}/photos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FileResponse> upload(
            @PathVariable UUID boardPointId,
            @Parameter(required = true, description = "photo file")
            @RequestPart("file") MultipartFile file,
            @AuthenticationPrincipal UserEntity currentUser)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(uploadBoardPointPhotoUseCase.execute(boardPointId, file, currentUser));
    }

    @GetMapping("/{boardPointId}/photos")
    public ResponseEntity<List<FileResponse>> list(
            @PathVariable UUID boardPointId,
            @AuthenticationPrincipal UserEntity currentUser
    ) {
        return ResponseEntity.ok(listBoardPointPhotosUseCase.execute(boardPointId, currentUser));
    }

    @GetMapping("/{boardPointId}/photos/{photoId}")
    public ResponseEntity<FileResponse> fetch(
            @PathVariable UUID boardPointId,
            @PathVariable UUID photoId,
            @AuthenticationPrincipal UserEntity currentUser
    ) {
        return ResponseEntity.ok(fetchBoardPointPhotoUseCase.execute(boardPointId, photoId, currentUser));
    }

    @PutMapping(value = "/{boardPointId}/photos/{photoId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FileResponse> update(
            @PathVariable UUID boardPointId,
            @PathVariable UUID photoId,
            @Parameter(required = true, description = "photo file")
            @RequestPart("file") MultipartFile file,
            @AuthenticationPrincipal UserEntity currentUser
    ) {
        return ResponseEntity.ok(updateBoardPointPhotoUseCase.execute(boardPointId, photoId, file, currentUser));
    }
}
