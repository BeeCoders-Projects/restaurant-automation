package com.beecoders.ras.repository.auth;

import com.beecoders.ras.model.auth.entity.Credential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CredentialRepository extends JpaRepository<Credential, Long> {

    Optional<Credential> findByUsername(String username);
}