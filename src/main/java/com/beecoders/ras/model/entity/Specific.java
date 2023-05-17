package com.beecoders.ras.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Specific {
    VEGETARIAN(1, "Vegetarian"),
    SPICY(2, "Spicy");

    private final int specificValue;
    private final String specificName;

}