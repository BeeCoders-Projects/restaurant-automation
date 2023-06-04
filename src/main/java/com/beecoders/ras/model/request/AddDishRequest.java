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
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("weight")
    private Integer weight;
    @JsonProperty("price")
    private Double price;
    @JsonProperty("specifics")
    private List<String> specifics;
    @JsonProperty("ingredientsId")
    private List<Long> ingredientsId;
    @JsonProperty("categoryId")
    private Long categoryId;

}