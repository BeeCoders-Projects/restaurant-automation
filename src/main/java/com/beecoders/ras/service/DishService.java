package com.beecoders.ras.service;

import com.amazonaws.util.CollectionUtils;
import com.beecoders.ras.exception.dish.DishNotFoundException;
import com.beecoders.ras.model.entity.Category;
import com.beecoders.ras.model.entity.Dish;
import com.beecoders.ras.model.entity.Ingredient;
import com.beecoders.ras.model.entity.Specific;
import com.beecoders.ras.model.mapper.DishMapper;
import com.beecoders.ras.model.request.AddDishRequest;
import com.beecoders.ras.model.request.ChangeSpecialDish;
import com.beecoders.ras.model.response.DishDetailInfo;
import com.beecoders.ras.model.response.DishInfo;
import com.beecoders.ras.repository.CategoryRepository;
import com.beecoders.ras.repository.DishRepository;
import com.beecoders.ras.repository.IngredientRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static com.beecoders.ras.model.constants.CategoryConstant.ALL;
import static com.beecoders.ras.model.constants.CategoryConstant.SPECIAL;
import static com.beecoders.ras.model.constants.DishConstant.DISH_FOLDER;
import static com.beecoders.ras.model.constants.DishConstant.NOT_FOUND_DISH_ERROR_MESSAGE;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class DishService {
    private final IngredientRepository ingredientRepository;
    private final CategoryRepository categoryRepository;
    private final DishRepository dishRepository;
    private final DishMapper dishMapper;
    private final ImageStoreService imageStoreService;
    private final ModelMapper mapper;

    public List<DishInfo> retrieveDishMenu(String category) {
        List<Dish> dishes;
        if (category.equals(ALL))
            dishes = dishRepository.findAll();
        else if (category.equals(SPECIAL))
            dishes = dishRepository.findAllSpecialDishes();
        else
            dishes = dishRepository.findAllByCategory(category);

        return dishMapper.toDishInformations(dishes.stream().sorted((o1, o2) -> o2.getCreatedAt().compareTo(o1.getCreatedAt())).toList());
    }

    public DishDetailInfo retrieveDishById(Long id) {
        return mapper.map(getDishById(id), DishDetailInfo.class);
    }

    @Transactional
    public void uploadImage(Long id, MultipartFile image) {
        Dish foundDish = dishRepository.findById(id)
                .orElseThrow(() -> new DishNotFoundException(NOT_FOUND_DISH_ERROR_MESSAGE));
        String iconLink = imageStoreService.uploadImage(image, DISH_FOLDER, id);

        foundDish.setIcon(iconLink);
        dishRepository.save(foundDish);
    }

    @Transactional
    public void setSpecialDish(ChangeSpecialDish request) {
        Dish foundDish = getDishById(request.getDishId());
        log.info("Change special request: {}", request);
        foundDish.setSpecial(request.isSpecial());
        dishRepository.save(foundDish);
    }

    private Dish getDishById(Long id) {
        return dishRepository.findById(id)
                .orElseThrow(() -> new DishNotFoundException(NOT_FOUND_DISH_ERROR_MESSAGE));
    }

    @Transactional
    public void saveAll(List<AddDishRequest> dishRequests, MultipartFile[] images){
        if(!CollectionUtils.isNullOrEmpty(dishRequests)){
            if(dishRequests.size()!=images.length)
                throw new IllegalArgumentException("Images are not provided for all dishes");
            List<Dish> dishes = dishRequests.stream().map(this::convertDishRequest).toList();
            dishes = dishRepository.saveAll(dishes);
            for (int i = 0; i < dishes.size(); i++) {
                Dish dish = dishes.get(i);
                String url = imageStoreService.uploadImage(images[i], DISH_FOLDER, dish.getId());
                dish.setIcon(url);
            }
        }
    }

    private Dish convertDishRequest(AddDishRequest dr){
        String specifics = dr.getSpecifics().stream()
                .map(sc->Specific.valueOf(sc.toUpperCase()).name())
                .collect(Collectors.joining(","));
        HashSet<Long> ingredientsIdSet = new HashSet<>(dr.getIngredientsId());
        List<Ingredient> ingredients = ingredientRepository.findAllById(ingredientsIdSet);
        if(ingredientsIdSet.size()!=ingredients.size())
            throw new IllegalArgumentException(String.format("One or more ingredients in the dish %s do not exist", dr.getName()));
        Category category = categoryRepository
                .findById(dr.getCategoryId())
                .orElseThrow(()-> new EntityNotFoundException(String.format("Category with id: %s not found", dr.getCategoryId())));
        return Dish.builder()
                .name(dr.getName())
                .description(dr.getDescription())
                .price(dr.getPrice())
                .weight(dr.getWeight())
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .specifics(specifics)
                .category(category)
                .ingredients(ingredients)
                .build();
    }

}