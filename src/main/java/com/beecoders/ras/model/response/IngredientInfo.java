package com.beecoders.ras.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngredientInfo {
    private String name;
    @JsonProperty(value = "is_allergic")
    private boolean isAllergic;
}
