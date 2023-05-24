package com.beecoders.ras.model.mapper;

import com.beecoders.ras.model.entity.Ingredient;
import com.beecoders.ras.model.response.dish.IngredientInfo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IngredientMapper {
    List<IngredientInfo> toIngredientInfoList(List<Ingredient> ingredients);
}
