package com.beecoders.ras.security.principal.service;

import com.beecoders.ras.auth.model.entity.Credential;
import com.beecoders.ras.auth.repository.CredentialRepository;
import com.beecoders.ras.security.principal.model.CredentialPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CredentialDetailService implements UserDetailsService {
    private final CredentialRepository credentialRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String errorMessage = "Credentials not found by username [ " + username + "]";
        Credential credential = credentialRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(errorMessage));

        return new CredentialPrincipal(credential);
    }
}
