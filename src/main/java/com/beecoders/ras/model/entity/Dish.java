package com.beecoders.ras.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Set;

@Table(name = "dishes")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 512)
    private String description;

    @Column(nullable = false, columnDefinition = "float(10,2)")
    private Double price;

    @Column
    private String icon;

    @Column(nullable = false)
    private int weight;

    @Column(nullable = false, name = "created_at")
    private Timestamp createdAt;

    @Column
    private String specifics;

    @ManyToOne
    @JoinColumn(name = "fk_category_id")
    private Category category;

    @ManyToMany
    @JoinTable(
            name = "dishes_ingredients",
            joinColumns = @JoinColumn(name = "fk_dish_id"),
            inverseJoinColumns = @JoinColumn(name = "fk_ingredient_id")
    )
    private Set<Ingredient> ingredients;

}