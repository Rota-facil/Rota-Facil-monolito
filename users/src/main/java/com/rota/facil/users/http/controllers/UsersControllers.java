package com.rota.facil.users.http.controllers;

import com.rota.facil.users.business.useCases.admin.ListDriversUseCase;
import com.rota.facil.users.business.useCases.admin.ListStudentsUseCase;
import com.rota.facil.users.business.useCases.users.ChangePrefectureUseCase;
import com.rota.facil.users.business.useCases.admin.CreateDriverUseCase;
import com.rota.facil.users.business.useCases.users.FetchMeUseCase;
import com.rota.facil.users.business.useCases.users.UpdateAccountUseCase;
import com.rota.facil.users.http.dto.request.users.CreateDriverRequest;
import com.rota.facil.users.http.dto.request.users.UpdateAccountRequest;
import com.rota.facil.users.http.dto.response.users.UserResponse;
import com.rota.facil.users.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersControllers {
    private final CreateDriverUseCase createDriverUseCase;
    private final ChangePrefectureUseCase changePrefectureUseCase;
    private final FetchMeUseCase fetchMeUseCase;
    private final UpdateAccountUseCase updateAccountUseCase;
    private final ListDriversUseCase listDriversUseCase;
    private final ListStudentsUseCase listStudentsUseCase;

    @PostMapping("/drivers")
    public ResponseEntity<UserResponse> createDriver(
            @AuthenticationPrincipal UserEntity currentUser,
            @RequestBody CreateDriverRequest createDriver
    ) {
        return ResponseEntity.ok(this.createDriverUseCase.execute(currentUser, createDriver));
    }

    @GetMapping("/drivers")
    ResponseEntity<List<UserResponse>> listDrivers(@AuthenticationPrincipal UserEntity currentUser) {
        return ResponseEntity.ok(this.listDriversUseCase.execute(currentUser));
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> fetchMe(@AuthenticationPrincipal UserEntity currentUser) {
        return ResponseEntity.ok(this.fetchMeUseCase.execute(currentUser));
    }

    @GetMapping("/students")
    public ResponseEntity<Page<UserResponse>> listStudents(
            @AuthenticationPrincipal UserEntity currentUser,
            @ParameterObject @PageableDefault Pageable pageable
    ) {
        return ResponseEntity.ok(this.listStudentsUseCase.execute(currentUser, pageable));
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
