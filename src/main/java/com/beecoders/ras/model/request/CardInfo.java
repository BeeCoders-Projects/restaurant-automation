package com.beecoders.ras.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CardInfo {
    @NotNull(message = "Number of card should not be blank")
    @Size(min = 13, max = 19, message = "Size of number of card should be between 13 and 19 digits")
    @Pattern(regexp = "\\d+", message = "Number of card should have only digits")
    private String number;
    @NotNull(message = "Expire date of card should not be blank")
    @Pattern(regexp = "^(0[1-9]|1[0-2])/\\d{2}$", message = "Invalid date format. Expected format: MM/yy")
    @JsonProperty(value = "expire_at")
    private String expiredAt;
    @NotNull(message = "CVV of card should not be blank")
    @Size(min = 3, max = 3, message = "Size of cvv of card should be only 3 digits")
    @Pattern(regexp = "\\d+", message = "Number of card should have only digits")
    private String cvv;
}
