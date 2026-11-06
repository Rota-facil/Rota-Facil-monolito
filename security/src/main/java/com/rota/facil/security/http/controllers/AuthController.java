package com.rota.facil.security.http.controllers;

import com.rota.facil.security.business.CompleteGoogleLoginUseCase;
import com.rota.facil.security.business.auth.CreateAccountUseCase;
import com.rota.facil.security.business.auth.LoginUseCase;
import com.rota.facil.security.http.dto.request.google.CompleteGoogleLoginRequest;
import com.rota.facil.security.http.dto.request.user.AuthLoginRequest;
import com.rota.facil.security.http.dto.request.user.CreateAccountRequest;
import com.rota.facil.security.http.dto.response.token.TokenResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final CreateAccountUseCase createAccountUseCase;
    private final CompleteGoogleLoginUseCase completeGoogleLoginUseCase;
    private final LoginUseCase loginUseCase;

    @PostMapping("/register")
    public ResponseEntity<TokenResponse> createAccount(@Valid @RequestBody CreateAccountRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.createAccountUseCase.execute(request));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody AuthLoginRequest request) {
        return ResponseEntity.ok(this.loginUseCase.execute(request));

    }

    @PostMapping("/complete/google")
    public ResponseEntity<TokenResponse> completeGoogleLogin(
            @RequestParam(name = "pendingToken") UUID pendingToken,
            @RequestBody CompleteGoogleLoginRequest request
    ) {
        return ResponseEntity.ok(this.completeGoogleLoginUseCase.execute(pendingToken, request));
    }
}
