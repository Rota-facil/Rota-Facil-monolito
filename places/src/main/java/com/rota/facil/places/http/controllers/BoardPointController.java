package com.rota.facil.places.http.controllers;

import com.rota.facil.places.business.boardpoints.CreateBoardPointUseCase;
import com.rota.facil.places.business.boardpoints.FetchBoardPointUseCase;
import com.rota.facil.places.http.dto.request.boardpoints.CreateBoardPointRequest;
import com.rota.facil.places.http.dto.response.boardpoints.CreateBoardPointResponse;
import com.rota.facil.places.http.dto.response.boardpoints.BoardPointResponse;
import com.rota.facil.users.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;
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
    private final FetchBoardPointUseCase fetchBoardPointUseCase;

    @PostMapping
    public ResponseEntity<CreateBoardPointResponse> createBoardPoints(
            @RequestBody CreateBoardPointRequest request,
            @AuthenticationPrincipal UserEntity currentUser
            ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.createBoardPointUseCase.execute(request, currentUser));
    }

    @GetMapping("/{boardPointId}")
    public ResponseEntity<BoardPointResponse> fetchBoardPoint(
            @PathVariable UUID boardPointId,
            @AuthenticationPrincipal UserEntity currentUser
    ) {
        return ResponseEntity.ok(fetchBoardPointUseCase.execute(boardPointId, currentUser));
    }
}
