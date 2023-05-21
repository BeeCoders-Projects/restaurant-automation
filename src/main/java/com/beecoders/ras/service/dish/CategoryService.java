package com.beecoders.ras.service.dish;

import com.beecoders.ras.model.mapper.CategoryMapper;
import com.beecoders.ras.model.response.dish.CategoryResponse;
import com.beecoders.ras.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public List<CategoryResponse> getAllCategories(){
        return categoryRepository.findAll()
                .stream().map(categoryMapper::toCategoryResponse).toList();
    }

}