package com.beecoders.ras.service.dish;

import com.beecoders.ras.model.mapper.DishMapper;
import com.beecoders.ras.model.response.dish.DishInfo;
import com.beecoders.ras.repository.DishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DishService {
    private final DishRepository dishRepository;
    private final DishMapper dishMapper;

    public List<DishInfo> retrieveDishMenu() {
        return dishMapper.toDishInformations(dishRepository.findAll());
    }
}
