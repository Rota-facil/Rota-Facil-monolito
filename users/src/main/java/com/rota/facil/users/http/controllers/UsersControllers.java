package com.rota.facil.users.http.controllers;

import com.rota.facil.prefectures.http.dto.response.prefecture.PrefectureResponse;
import com.rota.facil.users.business.users.ChangePrefectureUseCase;
import com.rota.facil.users.business.admin.CreateDriverUseCase;
import com.rota.facil.users.business.users.FetchMeUseCase;
import com.rota.facil.users.business.users.UpdateAccountUseCase;
import com.rota.facil.users.http.dto.request.users.CreateDriverRequest;
import com.rota.facil.users.http.dto.request.users.UpdateAccountRequest;
import com.rota.facil.users.http.dto.response.users.UserResponse;
import com.rota.facil.users.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersControllers {
    private final CreateDriverUseCase createDriverUseCase;
    private final ChangePrefectureUseCase changePrefectureUseCase;
    private final FetchMeUseCase fetchMeUseCase;
    private final UpdateAccountUseCase updateAccountUseCase;

    @PostMapping("/drivers")
    public ResponseEntity<UserResponse> createDriver(
            @AuthenticationPrincipal UserEntity currentUser,
            @RequestBody CreateDriverRequest createDriver
    ) {
        return ResponseEntity.ok(this.createDriverUseCase.execute(currentUser, createDriver));
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> fetchMe(@AuthenticationPrincipal UserEntity currentUser) {
        return ResponseEntity.ok(this.fetchMeUseCase.execute(currentUser));
    }

    @PutMapping("/update")
    public ResponseEntity<UserResponse> update(
            @AuthenticationPrincipal UserEntity currentUser,
            @RequestBody UpdateAccountRequest request
    ) {
        return ResponseEntity.ok(this.updateAccountUseCase.execute(currentUser, request));
    }

    @PatchMapping("/{prefectureId}/change")
    public ResponseEntity<UserResponse> changePrefecture(
            @PathVariable UUID prefectureId,
            @AuthenticationPrincipal UserEntity currentUser
    ) {
        return ResponseEntity.ok(this.changePrefectureUseCase.execute(prefectureId, currentUser));
    }

}
