package com.rota.facil.places.http.controllers;

import com.rota.facil.places.business.boardpoints.CreateBoardPointUseCase;
import com.rota.facil.places.business.boardpoints.DeleteBoardPointUseCase;
import com.rota.facil.places.business.boardpoints.FetchBoardPointUseCase;
import com.rota.facil.places.business.boardpoints.ListBoardPointsUseCase;
import com.rota.facil.places.business.boardpoints.UpdateBoardPointUseCase;
import com.rota.facil.places.http.dto.request.boardpoints.CreateBoardPointRequest;
import com.rota.facil.places.http.dto.request.boardpoints.UpdateBoardPointRequest;
import com.rota.facil.places.http.dto.response.boardpoints.CreateBoardPointResponse;
import com.rota.facil.places.http.dto.response.boardpoints.BoardPointResponse;
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
@RequestMapping("/board-points")
@RequiredArgsConstructor
public class BoardPointController {
    private final CreateBoardPointUseCase createBoardPointUseCase;
    private final DeleteBoardPointUseCase deleteBoardPointUseCase;
    private final FetchBoardPointUseCase fetchBoardPointUseCase;
    private final ListBoardPointsUseCase listBoardPointsUseCase;
    private final UpdateBoardPointUseCase updateBoardPointUseCase;

    @PostMapping
    public ResponseEntity<CreateBoardPointResponse> createBoardPoints(
            @Valid @RequestBody CreateBoardPointRequest request,
            @AuthenticationPrincipal UserEntity currentUser
            ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.createBoardPointUseCase.execute(request, currentUser));
    }

    @GetMapping
    public ResponseEntity<Page<BoardPointResponse>> listBoardPoints(
            @ParameterObject @PageableDefault Pageable pageable,
            @AuthenticationPrincipal UserEntity currentUser
    ) {
        return ResponseEntity.ok(listBoardPointsUseCase.execute(pageable, currentUser));
    }

    @GetMapping("/{boardPointId}")
    public ResponseEntity<BoardPointResponse> fetchBoardPoint(
            @PathVariable UUID boardPointId,
            @AuthenticationPrincipal UserEntity currentUser
    ) {
        return ResponseEntity.ok(fetchBoardPointUseCase.execute(boardPointId, currentUser));
    }

    @DeleteMapping("/{boardPointId}")
    public ResponseEntity<Void> deleteBoardPoint(
            @PathVariable UUID boardPointId,
            @AuthenticationPrincipal UserEntity currentUser
    ) {
        deleteBoardPointUseCase.execute(boardPointId, currentUser);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{boardPointId}")
    public ResponseEntity<BoardPointResponse> updateBoardPoint(
            @PathVariable UUID boardPointId,
            @Valid @RequestBody UpdateBoardPointRequest request,
            @AuthenticationPrincipal UserEntity currentUser
    ) {
        return ResponseEntity.ok(updateBoardPointUseCase.execute(boardPointId, request, currentUser));
    }
}
