package com.beecoders.ras.repository;

import com.beecoders.ras.model.entity.auth.AuthorizedAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorizedAccountRepository extends CrudRepository<AuthorizedAccount, String> {
    Optional<AuthorizedAccount> findByUsername(String username);
}
