package com.rota.facil.places.http.controllers;

import com.rota.facil.places.business.institutions.CreateInstitutionUseCase;
import com.rota.facil.places.http.dto.request.institutions.CreateInstitutionRequest;
import com.rota.facil.places.http.dto.response.institutions.CreateInstitutionResponse;
import com.rota.facil.users.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/institutions")
public class InstitutionController {
    private final CreateInstitutionUseCase createInstitutionUseCase;

    @PostMapping
    public ResponseEntity<CreateInstitutionResponse> createInstitution(
            @RequestBody CreateInstitutionRequest request,
            @AuthenticationPrincipal UserEntity currentUser
            ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.createInstitutionUseCase.execute(request, currentUser));
    }
}
