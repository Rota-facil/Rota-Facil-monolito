package com.rota.facil.vehicles.http.controllers;

import com.rota.facil.users.persistence.entities.UserEntity;
import com.rota.facil.vehicles.business.CreateVehicleUseCase;
import com.rota.facil.vehicles.http.dto.request.vehicles.CreateVehicleRequest;
import com.rota.facil.vehicles.http.dto.response.vehicles.CreateVehicleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vehicles")
@RequiredArgsConstructor
public class VehicleController {
    private final CreateVehicleUseCase createVehicleUseCase;

    @PostMapping
    public ResponseEntity<CreateVehicleResponse> createVehicle(
            @RequestBody CreateVehicleRequest request,
            @AuthenticationPrincipal UserEntity currentUser
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.createVehicleUseCase.execute(request, currentUser));
    }
}
