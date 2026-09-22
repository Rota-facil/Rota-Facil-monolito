package com.rota.facil.journey.http.controllers;

import com.rota.facil.journey.business.routes.ValidateCheckinUseCase;
import com.rota.facil.journey.business.trips.CreateTripUseCase;
import com.rota.facil.journey.business.trips.JoinInTripUseCase;
import com.rota.facil.journey.http.dto.request.routes.UserLocationCheakinRequest;
import com.rota.facil.journey.http.dto.request.trips.CreateTripRequest;
import com.rota.facil.journey.http.dto.request.trips.JoinInTripRequest;
import com.rota.facil.journey.http.dto.response.trips.TripResponse;
import com.rota.facil.journey.http.dto.response.trips.TripUserResponse;
import com.rota.facil.users.persistence.entities.UserEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/trips")
@RequiredArgsConstructor
public class TripController {
    private final CreateTripUseCase createTripUseCase;
    private final ValidateCheckinUseCase validateCheckinUseCase;
    private final JoinInTripUseCase joinInTripUseCase;

    @PostMapping
    public ResponseEntity<TripResponse> createTrip(
            @Valid @RequestBody CreateTripRequest request,
            @AuthenticationPrincipal UserEntity currentUser
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.createTripUseCase.execute(currentUser, request));
    }

    @PostMapping("trip/{tripId}/checkin")
    public ResponseEntity<Void> validateCheckin(
        @PathVariable UUID tripId,
        @AuthenticationPrincipal UserEntity currentUser,
        @RequestBody UserLocationCheakinRequest request
    ) {
        this.validateCheckinUseCase.execute(tripId, currentUser, request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/trip/{tripId}/join")
    public ResponseEntity<TripUserResponse> join(
            @PathVariable UUID tripId,
            @AuthenticationPrincipal UserEntity currentUser,
            @RequestBody JoinInTripRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.joinInTripUseCase.execute(tripId, currentUser, request));
    }
}
