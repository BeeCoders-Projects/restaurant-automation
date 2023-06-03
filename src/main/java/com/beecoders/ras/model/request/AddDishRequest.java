package com.beecoders.ras.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class AddDishRequest {
    private String name;
    private String description;
    private Integer weight;
    private Double price;
    private List<String> specifics;
    @JsonProperty("ingredientsId")
    private List<Long> ingredientsId;
    @JsonProperty("categoryId")
    private Long categoryId;

}