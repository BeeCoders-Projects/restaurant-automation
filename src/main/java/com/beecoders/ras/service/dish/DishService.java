package com.beecoders.ras.service.dish;

import com.beecoders.ras.exception.dish.DishNotFoundException;
import com.beecoders.ras.model.entity.Dish;
import com.beecoders.ras.model.mapper.DishDetailInfoMapper;
import com.beecoders.ras.model.mapper.DishMapper;
import com.beecoders.ras.model.response.dish.DishDetailInfo;
import com.beecoders.ras.model.response.dish.DishInfo;
import com.beecoders.ras.repository.DishRepository;
import com.beecoders.ras.service.s3.ImageStoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.beecoders.ras.model.constants.dish.DishConstant.DISH_FOLDER;
import static com.beecoders.ras.model.constants.dish.DishConstant.NOT_FOUND_DISH_ERROR_MESSAGE;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class DishService {
    private final DishRepository dishRepository;
    private final DishMapper dishMapper;
    private final ImageStoreService imageStoreService;
    private final ModelMapper mapper;

    public List<DishInfo> retrieveDishMenu() {
        return dishMapper.toDishInformations(dishRepository.findAll());
    }

    public List<DishInfo> retrieveDishMenuByCategory(Long categoryId){
        return dishMapper.toDishInformations(dishRepository.findAllByCategoryId(categoryId));
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

    private Dish getDishById(Long id) {
        return dishRepository.findById(id)
                .orElseThrow(() -> new DishNotFoundException(NOT_FOUND_DISH_ERROR_MESSAGE));
    }
}
