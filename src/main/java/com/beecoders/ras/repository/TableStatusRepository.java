package com.beecoders.ras.repository;

import com.beecoders.ras.model.entity.TableStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TableStatusRepository extends JpaRepository<TableStatus, Long> {
    Optional<TableStatus> findByNameIgnoreCase(String name);
}
