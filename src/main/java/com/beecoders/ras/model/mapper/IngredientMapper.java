package com.beecoders.ras.model.mapper;

import com.beecoders.ras.model.entity.Ingredient;
import com.beecoders.ras.model.response.dish.IngredientInfo;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Objects;

@Mapper(componentModel = "spring")
public interface IngredientMapper {
    List<IngredientInfo> toIngredientInfoList(List<Ingredient> ingredients);
    default Ingredient toIngredient(IngredientInfo ingredientInfo) {
        Objects.requireNonNull(ingredientInfo);
        return new Ingredient(null, ingredientInfo.getName(), ingredientInfo.isAllergic());
    }
}
