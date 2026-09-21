package com.rota.facil.places.http.controllers;

import com.rota.facil.places.business.institutions.CreateInstitutionUseCase;
import com.rota.facil.places.business.institutions.DeleteInstitutionUseCase;
import com.rota.facil.places.business.institutions.FetchInstitutionUseCase;
import com.rota.facil.places.business.institutions.ListInstitutionsUseCase;
import com.rota.facil.places.business.institutions.UpdateInstitutionUseCase;
import com.rota.facil.places.http.dto.request.institutions.CreateInstitutionRequest;
import com.rota.facil.places.http.dto.request.institutions.UpdateInstitutionRequest;
import com.rota.facil.places.http.dto.response.institutions.CreateInstitutionResponse;
import com.rota.facil.places.http.dto.response.institutions.InstitutionResponse;
import com.rota.facil.users.persistence.entities.UserEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/institutions")
public class InstitutionController {
    private final CreateInstitutionUseCase createInstitutionUseCase;
    private final DeleteInstitutionUseCase deleteInstitutionUseCase;
    private final FetchInstitutionUseCase fetchInstitutionUseCase;
    private final ListInstitutionsUseCase listInstitutionsUseCase;
    private final UpdateInstitutionUseCase updateInstitutionUseCase;

    @PostMapping
    public ResponseEntity<CreateInstitutionResponse> createInstitution(
            @Valid @RequestBody CreateInstitutionRequest request,
            @AuthenticationPrincipal UserEntity currentUser
            ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.createInstitutionUseCase.execute(request, currentUser));
    }

    @GetMapping
    public ResponseEntity<Page<InstitutionResponse>> listInstitutions(
            @ParameterObject @PageableDefault Pageable pageable,
            @AuthenticationPrincipal UserEntity currentUser
    ) {
        return ResponseEntity.ok(listInstitutionsUseCase.execute(pageable, currentUser));
    }

    @GetMapping("/{institutionId}")
    public ResponseEntity<InstitutionResponse> fetchInstitution(
            @PathVariable UUID institutionId,
            @AuthenticationPrincipal UserEntity currentUser
    ) {
        return ResponseEntity.ok(fetchInstitutionUseCase.execute(institutionId, currentUser));
    }

    @DeleteMapping("/{institutionId}")
    public ResponseEntity<Void> deleteInstitution(
            @PathVariable UUID institutionId,
            @AuthenticationPrincipal UserEntity currentUser
    ) {
        deleteInstitutionUseCase.execute(institutionId, currentUser);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{institutionId}")
    public ResponseEntity<InstitutionResponse> updateInstitution(
            @PathVariable UUID institutionId,
            @Valid @RequestBody UpdateInstitutionRequest request,
            @AuthenticationPrincipal UserEntity currentUser
    ) {
        return ResponseEntity.ok(updateInstitutionUseCase.execute(institutionId, request, currentUser));
    }
}
