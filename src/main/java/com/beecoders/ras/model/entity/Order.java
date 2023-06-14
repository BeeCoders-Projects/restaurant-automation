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

    @ManyToOne
    @JoinColumn(name = "fk_table_id", referencedColumnName = "id")
    private RestaurantTable table;

    @ManyToOne
    @JoinColumn(name = "fk_payment_method", referencedColumnName = "id")
    private PaymentMethod paymentMethod;

}