package com.beecoders.ras.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Table(name = "promocodes")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class Promocode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String code;

    @Column(name = "from_date", nullable = false)
    private LocalDate fromDate;

    @Column(name = "to_date", nullable = false)
    private LocalDate toDate;

    @Column(name = "holder_name", nullable = false)
    private String holderName;

    @Min(0)
    @Max(100)
    @Column(name = "discount_percent", nullable = false)
    private Byte discountPercent;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "promocode")
    private List<Order> orders;
}
