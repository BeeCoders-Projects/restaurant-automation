package com.beecoders.ras.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@EqualsAndHashCode
public class AddOrderRequest {
    @JsonProperty("dishes")
    private List<AddOrderDishRequest> dishes;

    @JsonProperty("order_id")
    private Long orderId;

}