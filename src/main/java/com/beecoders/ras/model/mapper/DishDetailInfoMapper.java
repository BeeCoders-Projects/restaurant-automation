package com.beecoders.ras.model.mapper;

import com.beecoders.ras.model.entity.Dish;
import com.beecoders.ras.model.entity.Specific;
import com.beecoders.ras.model.response.dish.DishDetailInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DishDetailInfoMapper extends AbstractConverter<Dish, DishDetailInfo> {
    private final IngredientMapper ingredientMapper;
    private final CategoryMapper categoryMapper;

    @Override
    protected DishDetailInfo convert(Dish dish) {
        return DishDetailInfo.builder()
                .id(dish.getId())
                .name(dish.getName())
                .description(dish.getDescription())
                .price(dish.getPrice())
                .icon(dish.getIcon())
                .weight(dish.getWeight())
                .specifics(convertSpecifics(dish.getSpecifics()))
                .category(categoryMapper.toCategoryDetail(dish.getCategory()))
                .ingredients(ingredientMapper.toIngredientInfoList(dish.getIngredients()))
                .build();
    }

    private List<Specific> convertSpecifics(String specifics){
        List<Specific> specificSet = new ArrayList<>();
        if(!StringUtils.isBlank(specifics)){
            Arrays.stream(specifics.trim().split(",")).forEach(s -> {
                try{
                    specificSet.add(getSpecificByName(s));
                } catch (IllegalArgumentException ignored){ }
            });
        }
        return specificSet;
    }

    private Specific getSpecificByName(String name) {
        return Arrays.stream(Specific.values()).filter(sp -> sp.getSpecificName().equals(name))
                .findFirst()
                .orElse(null);
    }

}