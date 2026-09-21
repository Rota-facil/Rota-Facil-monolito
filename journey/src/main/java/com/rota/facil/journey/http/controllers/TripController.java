package com.rota.facil.journey.http.controllers;

import com.rota.facil.journey.business.trips.CreateTripUseCase;
import com.rota.facil.journey.http.dto.request.trips.CreateTripRequest;
import com.rota.facil.journey.http.dto.response.trips.TripResponse;
import com.rota.facil.users.persistence.entities.UserEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trips")
@RequiredArgsConstructor
public class TripController {
    private final CreateTripUseCase createTripUseCase;

    public ResponseEntity<TripResponse> createTrip(
            @Valid @RequestBody CreateTripRequest request,
            @AuthenticationPrincipal UserEntity currentUser
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.createTripUseCase.execute(currentUser, request));
    }
}
