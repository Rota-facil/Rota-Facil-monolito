package com.rota.facil.journey.http.controllers;

import com.rota.facil.journey.business.routes.CreateRouteUseCase;
import com.rota.facil.journey.http.dto.request.routes.CreateRouteRequest;
import com.rota.facil.journey.http.dto.response.routes.CreateRouteResponse;
import com.rota.facil.users.persistence.entities.UserEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/routes")
@RequiredArgsConstructor
public class RouteController {
    private final CreateRouteUseCase createRouteUseCase;

    @PostMapping
    public ResponseEntity<CreateRouteResponse> createRoute(
            @Valid @RequestBody CreateRouteRequest request,
            @AuthenticationPrincipal UserEntity currentUser
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(createRouteUseCase.execute(currentUser, request));
    }
}
