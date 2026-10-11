package com.rota.facil.users.http.controllers;

import com.rota.facil.users.business.CreateDriverUseCase;
import com.rota.facil.users.http.dto.request.users.CreateDriverRequest;
import com.rota.facil.users.http.dto.response.users.UserResponse;
import com.rota.facil.users.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersControllers {
    private final CreateDriverUseCase createDriverUseCase;

    @PostMapping("/drivers")
    public ResponseEntity<UserResponse> createDriver(
            @AuthenticationPrincipal UserEntity currentUser,
            @RequestBody CreateDriverRequest createDriver
    ) {
        return ResponseEntity.ok(this.createDriverUseCase.execute(currentUser, createDriver));
    }
}
