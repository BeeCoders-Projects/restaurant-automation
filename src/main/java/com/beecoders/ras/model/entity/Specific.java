package com.beecoders.ras.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Specific {
    VEGETARIAN(1, "Vegetarian"),
    VEGAN(2, "Vegan"),
    SPICY(3, "Spicy"),
    HOT_SPICY(4, "Hot spicy"),
    LACTOSE(5, "Lactose"),
    GLUTEN(6, "Gluten");

    private final int specificValue;
    private final String specificName;

}