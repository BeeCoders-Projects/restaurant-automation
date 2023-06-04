package com.beecoders.ras.model.response;


import com.beecoders.ras.model.entity.Specific;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class DishDetailInfo {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String icon;
    private Integer weight;
    @JsonIgnore
    private List<Specific> specifics;
    private List<IngredientInfo> ingredients;
    private CategoryDetail category;
    private boolean isSpecial;

    @JsonProperty(value = "specifics_en")
    public List<String> getStringSpecificsEn(){
        return specifics.stream().map(Specific::getSpecificNameEn).toList();
    }
    @JsonProperty(value = "specifics_ua")
    public List<String> getStringSpecificsUa(){
        return specifics.stream().map(Specific::getSpecificNameUa).toList();
    }
}