package com.beecoders.ras.model.response.dish;


import com.beecoders.ras.model.entity.Category;
import com.beecoders.ras.model.entity.Specific;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class DishResponse {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String icon;
    private int weight;
    private Timestamp createdAt;
    private List<Specific> specifics;
    private Category category;

    public List<String> getStringSpecifics(){
        return specifics.stream().map(Specific::getSpecificName).toList();
    }
}