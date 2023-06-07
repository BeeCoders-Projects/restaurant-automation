package com.beecoders.ras.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableStatusChange {
    @NotBlank(message = "Name of table should not be blank")
    private String tableName;
    @NotBlank(message = "Status of table should not be blank")
    private String status;
}
