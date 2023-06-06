package com.beecoders.ras.service;

import com.antkorwin.xsync.XSync;
import com.beecoders.ras.model.entity.auth.Credential;
import com.beecoders.ras.model.request.AuthLogin;
import com.beecoders.ras.model.entity.auth.AuthorizedAccount;
import com.beecoders.ras.repository.AuthorizedAccountRepository;
import com.beecoders.ras.repository.CredentialRepository;
import com.beecoders.ras.security.jwt.service.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.atomic.AtomicReference;

import static com.beecoders.ras.model.constants.AuthConstant.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final CredentialRepository credentialRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorizedAccountRepository authorizedAccountRepository;
    private final XSync<String> xSync;

    public String login(AuthLogin login) {
        String username = login.getUsername();
        AtomicReference<String> jwtToken = new AtomicReference<>();

        xSync.execute(username, () -> {
            if (authorizedAccountRepository.findByUsername(username).isPresent())
                throw new BadCredentialsException(ALREADY_LOGIN_ERROR_MESSAGE);
            else {
                Credential foundCredential = credentialRepository.findByUsername(username)
                        .orElseThrow(() -> new BadCredentialsException(String.format(ACCOUNT_NOT_FOUND_ERROR_MESSAGE, username)));
                verifyPassword(login.getPassword(), foundCredential.getPassword());
                authenticate(login);
                jwtToken.set(jwtTokenProvider.generateJwtToken(foundCredential));
                authorizedAccountRepository.save(new AuthorizedAccount(username, jwtToken.get()));
            }
        });

        return jwtToken.get();
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
