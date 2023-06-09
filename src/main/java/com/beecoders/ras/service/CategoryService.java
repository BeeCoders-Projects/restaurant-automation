package com.beecoders.ras.service;

import com.beecoders.ras.model.constants.DishConstant;
import com.beecoders.ras.model.entity.Category;
import com.beecoders.ras.model.mapper.CategoryMapper;
import com.beecoders.ras.model.request.AddCategoryRequest;
import com.beecoders.ras.model.response.CategoryResponse;
import com.beecoders.ras.model.response.CategoryWithMetaData;
import com.beecoders.ras.repository.CategoryRepository;
import com.beecoders.ras.repository.DishRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final ImageStoreService imageStoreService;
    private final DishRepository dishRepository;

    public CategoryWithMetaData getAllCategories(){
        List<CategoryResponse> categories = categoryRepository.findAll()
                .stream().map(categoryMapper::toCategoryResponse).toList();
        boolean hasSpecialDishes = dishRepository.hasSpecialDishes();
        return new CategoryWithMetaData(categories, hasSpecialDishes);
    }

    @Transactional
    public CategoryResponse save(AddCategoryRequest addCategoryRequest, MultipartFile multipartFile){
        Category category = categoryRepository.save(categoryMapper.toCategory(addCategoryRequest));
        String url = imageStoreService.uploadImage(multipartFile, DishConstant.CATEGORY_FOLDER, category.getId());
        category.setIcon(url);
        return categoryMapper.toCategoryResponse(category);
    }

}