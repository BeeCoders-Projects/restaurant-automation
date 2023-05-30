package com.beecoders.ras.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Specific {
    VEGETARIAN(1, "Vegetarian", "Вегетаріанське"),
    VEGAN(2, "Vegan", "Веганське"),
    SPICY(3, "Spicy", "Гостро"),
    HOT_SPICY(4, "Hot spicy", "Пікантно-гостро"),
    LACTOSE(5, "Lactose", "Лактоза"),
    GLUTEN(6, "Gluten", "Глютен"),
    ALCOHOL(7, "Alcohol", "Алкоголь");

    private final int specificValue;
    private final String specificNameEn;
    private final String specificNameUa;

}