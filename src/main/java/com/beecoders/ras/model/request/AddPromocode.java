package com.beecoders.ras.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddPromocode {
    @JsonProperty("order_id")
    @NotNull(message = "Order id should not be null")
    private Long orderId;
    @NotBlank(message = "Promocode should not be blank")
    private String promocode;
}
