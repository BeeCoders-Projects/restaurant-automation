package com.beecoders.ras.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderDishInfo {
    private Long id;
    private String icon;
    private String name;
    private Long quantity;
    private Double price;
    private Integer weight;
}
