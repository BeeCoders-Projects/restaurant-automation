package com.beecoders.ras.model.response.dish;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DishInfo {
    private Long id;
    private String icon;
    private String name;
    private Double price;
    private int weight;
    private boolean isSpecial;
}
