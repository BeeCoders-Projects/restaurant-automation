package com.beecoders.ras.model.mapper;

import com.beecoders.ras.model.entity.Category;
import com.beecoders.ras.model.response.CategoryDetail;
import com.beecoders.ras.model.request.AddCategoryRequest;
import com.beecoders.ras.model.response.CategoryResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryResponse toCategoryResponse(Category category);
    CategoryDetail toCategoryDetail(Category category);
    Category toCategory(AddCategoryRequest addCategoryRequest);
}