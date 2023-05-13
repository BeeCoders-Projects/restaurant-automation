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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.beecoders.ras.model.auth.constants.AuthConstant.INCORRECT_LOGIN_ERROR_MESSAGE;
import static com.beecoders.ras.model.auth.constants.AuthConstant.ACCOUNT_NOT_FOUND_ERROR_MESSAGE;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final CredentialRepository credentialRepository;
    private final PasswordEncoder passwordEncoder;

    public String login(AuthLogin login) {
        String username = login.getUsername();
        Credential foundCredential = credentialRepository.findByUsername(username)
            .orElseThrow(() -> new BadCredentialsException(String.format(ACCOUNT_NOT_FOUND_ERROR_MESSAGE, username)));

        verifyPassword(login.getPassword(), foundCredential.getPassword());
        authenticate(login);

        return jwtTokenProvider.generateJwtToken(foundCredential);
    }

    private void authenticate(AuthLogin login) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());
        var authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void verifyPassword(String inputtedPassword, String credentialPassword) {
        if (!passwordEncoder.matches(inputtedPassword, credentialPassword))
            throw new BadCredentialsException(INCORRECT_LOGIN_ERROR_MESSAGE);
    }
}
