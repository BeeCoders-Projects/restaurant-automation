package com.beecoders.ras.repository;

import com.beecoders.ras.model.entity.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {
    @Query("select d from Dish d join d.category c where c.name = :category")
    List<Dish> findAllByCategory(String category);
    @Query("select d from Dish d where d.isSpecial = true")
    List<Dish> findAllSpecialDishes();
    @Query("select case when count(d) > 0 then true else false end from Dish d where d.isSpecial = true")
    boolean hasSpecialDishes();
}