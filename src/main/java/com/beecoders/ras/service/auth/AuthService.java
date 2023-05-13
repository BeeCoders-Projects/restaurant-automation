package com.beecoders.ras.service.auth;

import com.beecoders.ras.model.auth.entity.Credential;
import com.beecoders.ras.model.auth.request.AuthLogin;
import com.beecoders.ras.repository.auth.CredentialRepository;
import com.beecoders.ras.security.jwt.service.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final CredentialRepository credentialRepository;


    public String login(AuthLogin login) {
        authenticate(login);
        String errorMessage = "Account with email [" + login.getUsername() + "] does not exist";
        Credential foundCredential = credentialRepository.findByUsername(login.getUsername())
                .orElseThrow(() -> new BadCredentialsException(errorMessage));
        return jwtTokenProvider.generateJwtToken(foundCredential);
    }

    private void authenticate(AuthLogin login) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());
        var authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
