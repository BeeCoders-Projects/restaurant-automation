package com.beecoders.ras.auth.repository;

import com.beecoders.ras.auth.model.entity.Credential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CredentialRepository extends JpaRepository<Credential, Long> {

    Optional<Credential> findByUsername(String username);
}
