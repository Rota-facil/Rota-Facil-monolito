package com.rota.facil.files.http.controllers;

import com.rota.facil.files.business.useCases.users.FetchProfilePicUseCase;
import com.rota.facil.files.business.useCases.users.UploadProfilePicUseCase;
import com.rota.facil.files.http.dto.response.FileResponse;
import com.rota.facil.users.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files/users/profile-pic")
@RequiredArgsConstructor
public class ProfilePicController {
    private final UploadProfilePicUseCase uploadProfilePicUseCase;
    private final FetchProfilePicUseCase fetchProfilePicUseCase;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FileResponse> upload(@AuthenticationPrincipal UserEntity currentUser, @RequestParam("file") MultipartFile file) { return ResponseEntity.ok(uploadProfilePicUseCase.execute(currentUser, file)); }

    @GetMapping
    public ResponseEntity<FileResponse> fetch(@AuthenticationPrincipal UserEntity currentUser) { return ResponseEntity.ok(fetchProfilePicUseCase.execute(currentUser)); }
}
