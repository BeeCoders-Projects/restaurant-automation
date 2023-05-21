package com.beecoders.ras.model.mapper;

import com.beecoders.ras.model.entity.Category;
import com.beecoders.ras.model.response.dish.CategoryResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryResponse toCategoryResponse(Category category);

}