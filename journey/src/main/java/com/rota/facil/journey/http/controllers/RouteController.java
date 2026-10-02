package com.rota.facil.journey.http.controllers;

import com.rota.facil.journey.business.routes.CreateRouteUseCase;
import com.rota.facil.journey.business.routes.FetchRouteByIdUseCase;
import com.rota.facil.journey.business.routes.ListRoutesUseCase;
import com.rota.facil.journey.business.routes.UpdateRouteUseCase;
import com.rota.facil.journey.http.dto.request.routes.CreateRouteRequest;
import com.rota.facil.journey.http.dto.request.routes.UpdateRouteRequest;
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
import java.util.UUID;

@RestController
@RequestMapping("/routes")
@RequiredArgsConstructor
public class RouteController {
    private final CreateRouteUseCase createRouteUseCase;
    private final ListRoutesUseCase listRoutesUseCase;
    private final FetchRouteByIdUseCase fetchRouteByIdUseCase;
    private final UpdateRouteUseCase updateRouteUseCase;

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

    @GetMapping("/{routeId}")
    public ResponseEntity<RouteResponse> fetchRoute(
            @AuthenticationPrincipal UserEntity currentUser,
            @PathVariable UUID routeId
            ) {
        return ResponseEntity.ok(this.fetchRouteByIdUseCase.execute(currentUser, routeId));
    }

    @PutMapping("/{routeId}")
    public ResponseEntity<RouteResponse> updateRoute(
            @PathVariable UUID routeId,
            @AuthenticationPrincipal UserEntity currentUser,
            @RequestBody UpdateRouteRequest request
    ) {
        return ResponseEntity.ok(this.updateRouteUseCase.execute(currentUser, request, routeId));
    }
}
