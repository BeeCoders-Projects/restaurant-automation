package com.beecoders.ras.model.response.dish;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CategoryWithMetaData {
    private List<CategoryResponse> content;
    private boolean hasSpecialDishes;
}
