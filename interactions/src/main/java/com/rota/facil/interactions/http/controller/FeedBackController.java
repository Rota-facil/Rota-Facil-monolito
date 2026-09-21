package com.rota.facil.interactions.http.controller;

import com.rota.facil.interactions.business.feedbacks.ListFeedBacksByUserUseCase;
import com.rota.facil.interactions.business.feedbacks.SendFeedbackToUserUseCase;
import com.rota.facil.interactions.http.dto.request.feedbacks.SendFeedBackRequest;
import com.rota.facil.interactions.http.dto.response.feedbacks.FeedBackResponse;
import com.rota.facil.users.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/feedbacks")
@RequiredArgsConstructor
public class FeedBackController {
    private final SendFeedbackToUserUseCase sendFeedbackToUserUseCase;
    private final ListFeedBacksByUserUseCase listFeedBacksByUserUseCase;

    @PostMapping
    public ResponseEntity<FeedBackResponse> sendFeedback(
            @RequestBody SendFeedBackRequest request,
            @AuthenticationPrincipal UserEntity currentUser
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.sendFeedbackToUserUseCase.execute(currentUser, request));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<FeedBackResponse>> listFeedBacks(
            @AuthenticationPrincipal UserEntity currentUser,
            @PathVariable UUID userId
    ) {
        return ResponseEntity.ok(this.listFeedBacksByUserUseCase.execute(currentUser, userId));
    }
}
