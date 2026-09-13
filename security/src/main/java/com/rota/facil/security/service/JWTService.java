package com.rota.facil.security.service;

import com.rota.facil.users.entities.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JWTService {
    private final PublicKey publicKey;
    private final PrivateKey privateKey;


    public String generateAccessToken(UserEntity saved) {
        return this.generateToken(saved, 1000L);
    }

    public String generateRefreshToken(UserEntity saved) {
        return this.generateToken(saved, 10000L);
    }

    private String generateToken(UserEntity saved, Long expirationTime) {
        return Jwts.builder()
                .subject(saved.getEmail())
                .claim("userId", saved.getId())
                .claim("prefectureId", saved.getPrefectureId())
                .claim("role", saved.getRole())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(this.privateKey)
                .compact();
    }

    public boolean validateToken(String token) {
        return new Date(System.currentTimeMillis()).before(extractDate(token));
    }

    public Date extractDate(String token) {
        return this.extractClaims(token, Claims::getExpiration);
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction) {
        return  claimsTFunction.apply(extractAllClaims(token));
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(this.publicKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
