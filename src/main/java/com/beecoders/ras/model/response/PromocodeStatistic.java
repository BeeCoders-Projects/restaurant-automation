package com.beecoders.ras.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PromocodeStatistic {
    private String holderName;
    private Long count;
}
