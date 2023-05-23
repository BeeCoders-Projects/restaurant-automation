package com.beecoders.ras.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "ingredients")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "is_allergic", nullable = false)
    private boolean isAllergic;


}
