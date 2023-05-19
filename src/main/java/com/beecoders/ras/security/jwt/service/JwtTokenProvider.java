package com.beecoders.ras.security.jwt.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.beecoders.ras.model.entity.auth.Credential;

import com.beecoders.ras.repository.RestaurantTableRepository;
import lombok.RequiredArgsConstructor;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import java.util.Date;

import java.util.Objects;

import java.util.List;


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

    public Authentication getAuthentication(String username, GrantedAuthority authority, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(username, null, List.of(authority));

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return authenticationToken;
    }
    public String getSubject(String token) {
        return JWT.decode(token).getSubject();
    }

    private boolean isTokenExpired(String token) {
        Date expiration = JWT.decode(token).getExpiresAt();
        return expiration.before(new Date());
    }

    public boolean isTokenValid(String username, String token) {
        return StringUtils.isNotEmpty(username) &&
                !isTokenExpired(token) &&
                getSubject(token).equals(username);
    }
    public GrantedAuthority getAuthority(String token) {
        return new SimpleGrantedAuthority(getRoleFromToken(token));
    }


    private String getRoleFromToken(String token) {
        JWTVerifier verifier = getVerifier();
        return verifier.verify(token).getClaim(ROLE_CLAIM).asString();
    }

    private JWTVerifier getVerifier() {
        JWTVerifier verifier;
        try {
            Algorithm algorithm = Algorithm.HMAC512(secret);
            verifier = JWT.require(algorithm).withIssuer(TOKEN_ISSUE).build();
        } catch (JWTVerificationException e) {
            throw new JWTVerificationException(TOKEN_NOT_VERIFIED_MESSAGE);
        }
        return verifier;

    }
}
