package com.rota.facil.security.service;

import com.rota.facil.security.business.auth.helpers.FindUserTokenByAccessTokenHelper;
import com.rota.facil.security.persistence.entities.UserTokenEntity;
import com.rota.facil.security.persistence.repositories.UserTokenRepository;
import com.rota.facil.users.persistence.entities.UserEntity;
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
    private final UserTokenRepository userTokenRepository;
    private final FindUserTokenByAccessTokenHelper findUserTokenByAccessTokenHelper;

    public UserTokenEntity generateTokenForNewUser(UserEntity userEntity) {
        UserTokenEntity userToken = this.userTokenRepository.findByUserId(userEntity.getId())
                .map(tokenFound -> {
                    tokenFound.setAccessToken(this.generateAccessToken(userEntity));
                    return tokenFound;
                })
                .orElseGet(() -> UserTokenEntity.builder()
                        .accessToken(this.generateAccessToken(userEntity))
                        .refreshToken(this.generateRefreshToken(userEntity))
                        .user(userEntity)
                        .build()
                );

        return this.userTokenRepository.save(userToken);
    }

    public String generateAccessToken(UserEntity saved) {
        return this.generateToken(saved, 100000L);
    }

    public String generateRefreshToken(UserEntity saved) {
        return this.generateToken(saved, 1000000L);
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
        UserTokenEntity tokenFound = this.findUserTokenByAccessTokenHelper.execute(token);
        return new Date(System.currentTimeMillis()).before(extractDate(token));
    }

    public Date extractDate(String token) {
        return this.extractClaims(token, Claims::getExpiration);
    }

    public String extractEmail(String token) {
        return this.extractClaims(token, Claims::getSubject);
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
