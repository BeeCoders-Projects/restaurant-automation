package com.beecoders.ras.model.mapper;

import com.beecoders.ras.model.entity.Dish;
import com.beecoders.ras.model.entity.Specific;
import com.beecoders.ras.model.response.DishResponse;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DishResponseMapper extends AbstractConverter<Dish, DishResponse> {
    @Override
    protected DishResponse convert(Dish dish) {
        return DishResponse.builder()
                .id(dish.getId())
                .name(dish.getName())
                .description(dish.getDescription())
                .price(dish.getPrice())
                .icon(dish.getIcon())
                .weight(dish.getWeight())
                .createdAt(dish.getCreatedAt())
                .specifics(convertSpecifics(dish.getSpecifics()))
                .build();
    }

    private List<Specific> convertSpecifics(String specifics){
        List<Specific> specificSet = new ArrayList<>();
        if(!StringUtils.isBlank(specifics)){
            Arrays.stream(specifics.trim().split(",")).forEach(s -> {
                try{
                    specificSet.add(Specific.valueOf(s));
                } catch (IllegalArgumentException ignored){ }
            });
        }
        return specificSet;
    }

}