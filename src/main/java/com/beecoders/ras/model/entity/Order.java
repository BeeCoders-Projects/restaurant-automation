package com.beecoders.ras.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Table(name = "orders")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="created_at", nullable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;

    @Column(name = "finished_at")
    private Timestamp finishedAt;

    @Column(name = "total_price", nullable = false, columnDefinition = "float(10,2)")
    private Double totalPrice;

    @Column(name = "discount_sum", nullable = false, columnDefinition = "float(10,2) default 0")
    private Double discountSum;

    @Column(name = "current_sum", nullable = false, columnDefinition = "float(10,2) default 0")
    private Double currentSum;

    @ManyToOne
    @JoinColumn(name = "fk_table_id", referencedColumnName = "id")
    private RestaurantTable table;

    @ManyToOne
    @JoinColumn(name = "fk_payment_method", referencedColumnName = "id")
    private PaymentMethod paymentMethod;

    @ManyToOne
    @JoinColumn(name = "fk_promocode_id", referencedColumnName = "id")
    private Promocode promocode;

}