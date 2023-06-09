package com.beecoders.ras.repository;

import com.beecoders.ras.model.entity.OrderDish;
import com.beecoders.ras.model.response.OrderDishInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDishRepository extends JpaRepository<OrderDish, Long> {
    @Query("select new com.beecoders.ras.model.response.OrderDishInfo(od.dish.id, od.dish.icon, od.dish.name, " +
            "sum(od.quantity), sum(od.price), od.dish.weight) " +
            "from OrderDish od " +
            "where od.order.id = :id " +
            "group by od.dish.id, od.dish.icon, od.dish.name, od.dish.weight")
    List<OrderDishInfo> retrieveOrderedDishesByOrderId(Long id);
}
