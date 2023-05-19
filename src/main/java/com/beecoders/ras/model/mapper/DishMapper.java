package com.beecoders.ras.model.mapper;

import com.beecoders.ras.model.entity.Dish;
import com.beecoders.ras.model.response.dish.DishInformation;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DishMapper  {
    List<DishInformation> toDishInformations(List<Dish> dishes);
}
