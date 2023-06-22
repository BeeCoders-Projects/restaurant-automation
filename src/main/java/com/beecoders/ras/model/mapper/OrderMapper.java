package com.beecoders.ras.model.mapper;

import com.beecoders.ras.model.entity.Dish;
import com.beecoders.ras.model.entity.Order;
import com.beecoders.ras.model.entity.OrderDish;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    default OrderDish toOrderDish(Order order, Dish dish, int quantity) {
        return OrderDish.builder()
                .order(order)
                .dish(dish)
                .quantity(quantity)
                .price(dish.getPrice() * quantity)
                .build();
    }
}
