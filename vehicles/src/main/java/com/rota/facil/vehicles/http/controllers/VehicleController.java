package com.rota.facil.vehicles.http.controllers;

import com.rota.facil.users.persistence.entities.UserEntity;
import com.rota.facil.vehicles.business.CreateVehicleUseCase;
import com.rota.facil.vehicles.business.DeleteVehicleUseCase;
import com.rota.facil.vehicles.business.FetchVehicleUseCase;
import com.rota.facil.vehicles.business.ListVehiclesUseCase;
import com.rota.facil.vehicles.business.UpdateVehicleUseCase;
import com.rota.facil.vehicles.http.dto.request.vehicles.CreateVehicleRequest;
import com.rota.facil.vehicles.http.dto.request.vehicles.UpdateVehicleRequest;
import com.rota.facil.vehicles.http.dto.response.vehicles.CreateVehicleResponse;
import com.rota.facil.vehicles.http.dto.response.vehicles.FetchVehicleResponse;
import com.rota.facil.vehicles.http.dto.response.vehicles.ListVehicleResponse;
import com.rota.facil.vehicles.http.dto.response.vehicles.UpdateVehicleResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/vehicles")
@RequiredArgsConstructor
public class VehicleController {
    private final CreateVehicleUseCase createVehicleUseCase;
    private final DeleteVehicleUseCase deleteVehicleUseCase;
    private final FetchVehicleUseCase fetchVehicleUseCase;
    private final ListVehiclesUseCase listVehiclesUseCase;
    private final UpdateVehicleUseCase updateVehicleUseCase;

    @PostMapping
    public ResponseEntity<CreateVehicleResponse> createVehicle(
            @Valid @RequestBody CreateVehicleRequest request,
            @AuthenticationPrincipal UserEntity currentUser
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.createVehicleUseCase.execute(request, currentUser));
    }

    @GetMapping("/{vehicleId}")
    public ResponseEntity<FetchVehicleResponse> fetchVehicle(
            @PathVariable UUID vehicleId,
            @AuthenticationPrincipal UserEntity currentUser
    ) {
        return ResponseEntity.ok(this.fetchVehicleUseCase.execute(vehicleId, currentUser));
    }

    @GetMapping
    public ResponseEntity<List<ListVehicleResponse>> listVehicles(
            @AuthenticationPrincipal UserEntity currentUser
    ) {
        return ResponseEntity.ok(this.listVehiclesUseCase.execute(currentUser));
    }

    @DeleteMapping("/{vehicleId}")
    public ResponseEntity<Void> deleteVehicle(
            @PathVariable UUID vehicleId,
            @AuthenticationPrincipal UserEntity currentUser
    ) {
        this.deleteVehicleUseCase.execute(vehicleId, currentUser);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{vehicleId}")
    public ResponseEntity<UpdateVehicleResponse> updateVehicle(
            @PathVariable UUID vehicleId,
            @Valid @RequestBody UpdateVehicleRequest request,
            @AuthenticationPrincipal UserEntity currentUser
    ) {
        return ResponseEntity.ok(this.updateVehicleUseCase.execute(vehicleId, request, currentUser));
    }
}
