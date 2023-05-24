package com.beecoders.ras.model.mapper;

import com.beecoders.ras.model.entity.Ingredient;
import com.beecoders.ras.model.response.dish.IngredientInfo;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface IngredientMapper {
    Set<IngredientInfo> toIngedientInfos(Set<Ingredient> ingredients);
}
