package com.beecoders.ras.model.response.dish;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DishInfo {
    private Long id;
    private String image;
    private String name;
    private Double price;
    private int weight;
}
