package com.beecoders.ras.security.jwt.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.beecoders.ras.model.entity.auth.Credential;
import com.beecoders.ras.repository.RestaurantTableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

import static com.beecoders.ras.model.constants.auth.AuthConstant.TABLE_ROLE;
import static com.beecoders.ras.security.jwt.constant.JwtTokenConstant.*;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    @Value("${jwt.secret}")
    private String secret;

    private final RestaurantTableRepository restaurantTableRepository;

    public String generateJwtToken(Credential credential) {
        return JWT.create()
                .withIssuer(TOKEN_ISSUE)
                .withAudience()
                .withIssuedAt(new Date())
                .withSubject(credential.getUsername())
                .withClaim(NAME_CLAIM, getName(credential))
                .withClaim(ROLE_CLAIM, credential.getRole().getName())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(secret.getBytes()));
    }

    private String getName(Credential credential) {
        return (Objects.equals(credential.getRole().getName(), TABLE_ROLE))
                ? getTableName(credential) : credential.getUsername();
    }

    private String getTableName(Credential credential) {
        return restaurantTableRepository.findByCredential(credential).get().getName();
    }
}
