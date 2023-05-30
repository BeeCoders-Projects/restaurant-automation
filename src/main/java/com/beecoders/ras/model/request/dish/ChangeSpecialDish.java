package com.beecoders.ras.model.request.dish;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChangeSpecialDish {
    private Long dishId;
    @JsonProperty(value = "is_special")
    private boolean isSpecial;
}
