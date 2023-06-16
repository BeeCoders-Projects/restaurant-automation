package com.beecoders.ras.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class OrderDetailInfo {
    private Long orderId;
    private List<OrderDishInfo> dishes;
    private Double totalPrice;
    private Double discountSum;
    private Double currentSum;
}
