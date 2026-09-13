package com.rota.facil.security.http.controllers;

import com.rota.facil.security.business.auth.CreateAccountUseCase;
import com.rota.facil.security.http.dto.request.user.CreateAccountRequest;
import com.rota.facil.security.http.dto.response.token.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final CreateAccountUseCase createAccountUseCase;

    @PostMapping("/register")
    public ResponseEntity<TokenResponse> createAccount(@RequestBody CreateAccountRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(createAccountUseCase.execute(request));
    }
}
