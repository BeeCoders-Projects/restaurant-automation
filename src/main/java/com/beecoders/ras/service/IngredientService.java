package com.beecoders.ras.service;

import com.beecoders.ras.model.mapper.IngredientMapper;
import com.beecoders.ras.model.response.IngredientInfo;
import com.beecoders.ras.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class IngredientService {
    private final IngredientRepository ingredientRepository;
    private final IngredientMapper ingredientMapper;

    public void saveAll(List<IngredientInfo> ingredients) {
        if (!ingredients.isEmpty())
            ingredientRepository.saveAll(ingredients.stream().map(ingredientMapper::toIngredient).toList());
    }

}
