package com.beecoders.ras.security.principal.model;

import com.beecoders.ras.model.auth.entity.Credential;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CredentialPrincipal implements UserDetails {

    private final String username;
    private final String password;
    private final String role;

    public CredentialPrincipal(Credential credential) {
        this.username = credential.getUsername();
        this.password = credential.getPassword();
        this.role = credential.getRole().getName();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
