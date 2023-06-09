package com.beecoders.ras.repository;

import com.beecoders.ras.model.entity.OrderDish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDishRepository extends JpaRepository<OrderDish, Long> {
}
