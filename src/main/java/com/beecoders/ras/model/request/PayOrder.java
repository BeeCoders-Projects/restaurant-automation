package com.beecoders.ras.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PayOrder {
    @NotNull
    @JsonProperty(value = "order_id")
    private Long orderId;
    @NotBlank
    @JsonProperty(value = "payment_type")
    @Pattern(regexp = "DEBIT|CASH", message = "Payment type should be CASH or DEBIT")
    private String paymentType;
    @Valid
    @JsonProperty(value = "card_info")
    private CardInfo cardInfo;
}
