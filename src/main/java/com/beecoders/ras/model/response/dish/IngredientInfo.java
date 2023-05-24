package com.beecoders.ras.model.response.dish;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IngredientInfo {
    private String name;
    private boolean isAllergic;
}
