package com.rota.facil.journey.http.controllers;

import com.rota.facil.journey.business.routes.CreateRouteUseCase;
import com.rota.facil.journey.business.routes.ListRoutesUseCase;
import com.rota.facil.journey.http.dto.request.routes.CreateRouteRequest;
import com.rota.facil.journey.http.dto.response.routes.CreateRouteResponse;
import com.rota.facil.journey.http.dto.response.routes.RouteResponse;
import com.rota.facil.users.persistence.entities.UserEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/routes")
@RequiredArgsConstructor
public class RouteController {
    private final CreateRouteUseCase createRouteUseCase;
    private final ListRoutesUseCase listRoutesUseCase;

    @PostMapping
    public ResponseEntity<RouteResponse> createRoute(
            @Valid @RequestBody CreateRouteRequest request,
            @AuthenticationPrincipal UserEntity currentUser
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.createRouteUseCase.execute(currentUser, request));
    }

    @GetMapping
    public ResponseEntity<List<RouteResponse>> listRoutes(
            @AuthenticationPrincipal UserEntity currentUser
    ) {
        return ResponseEntity.ok(this.listRoutesUseCase.execute(currentUser));
    }
}
