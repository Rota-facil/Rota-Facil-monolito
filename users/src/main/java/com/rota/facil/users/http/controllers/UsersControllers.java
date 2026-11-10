package com.rota.facil.users.http.controllers;

import com.rota.facil.users.business.CreateDriverUseCase;
import com.rota.facil.users.business.FetchMeUseCase;
import com.rota.facil.users.http.dto.request.users.CreateDriverRequest;
import com.rota.facil.users.http.dto.response.users.UserResponse;
import com.rota.facil.users.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersControllers {
    private final CreateDriverUseCase createDriverUseCase;
    private final FetchMeUseCase fetchMeUseCase;

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
}
