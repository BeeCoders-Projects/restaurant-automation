package com.beecoders.ras.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "orders_dishes")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class OrderDish {
    @Id
    @ManyToOne
    @JoinColumn(name = "fk_order_id")
    private Order order;

    @Id
    @ManyToOne
    @JoinColumn(name = "fk_dish_id")
    private Dish dish;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false, columnDefinition = "float(10,2)")
    private Double price;
}
