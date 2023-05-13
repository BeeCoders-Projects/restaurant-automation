package com.beecoders.ras.security.jwt.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.beecoders.ras.model.auth.entity.Credential;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.beecoders.ras.security.jwt.constant.JwtTokenConstant.*;

@Component
public class JwtTokenProvider {
    @Value("${jwt.secret}")
    private String secret;

    public String generateJwtToken(Credential credential) {
        return JWT.create()
                .withIssuer(TOKEN_ISSUE)
                .withAudience()
                .withIssuedAt(new Date())
                .withSubject(credential.getUsername())
                .withClaim(ROLE_CLAIM, credential.getRole().getName())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(secret.getBytes()));
    }
}
