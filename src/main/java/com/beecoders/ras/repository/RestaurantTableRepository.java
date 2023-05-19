package com.beecoders.ras.repository;

import com.beecoders.ras.model.entity.RestaurantTable;
import com.beecoders.ras.model.entity.auth.Credential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, Long> {
    Optional<RestaurantTable> findByCredential(Credential credential);
}
