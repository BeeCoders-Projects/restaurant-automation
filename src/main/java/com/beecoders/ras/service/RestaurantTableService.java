package com.beecoders.ras.service;

import com.beecoders.ras.exception.dish.DishNotFoundException;
import com.beecoders.ras.exception.restaurant_table.RestaurantTableNotFoundException;
import com.beecoders.ras.exception.restaurant_table.TableStatusNotFoundException;
import com.beecoders.ras.model.constants.RestaurantTableConstant;
import com.beecoders.ras.model.entity.RestaurantTable;
import com.beecoders.ras.model.entity.TableStatus;
import com.beecoders.ras.model.request.TableStatusChange;
import com.beecoders.ras.repository.RestaurantTableRepository;
import com.beecoders.ras.repository.TableStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static com.beecoders.ras.model.constants.RestaurantTableConstant.*;

@Service
@RequiredArgsConstructor
@Transactional
public class RestaurantTableService {
    private final RestaurantTableRepository restaurantTableRepository;
    private final TableStatusRepository tableStatusRepository;

    public void updateStatus(TableStatusChange request) {
        RestaurantTable table = restaurantTableRepository.findByName(request.getTableName())
                .orElseThrow(() -> new RestaurantTableNotFoundException(TABLE_NOT_FOUND_ERROR_MESSAGE));
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TableStatus status = tableStatusRepository.findByNameIgnoreCase(request.getStatus())
                .orElseThrow(() -> new TableStatusNotFoundException(STATUS_TABLE_NOT_FOUND_ERROR_MESSAGE));

        if (!username.equals(table.getCredential().getUsername()))
            throw new AccessDeniedException(CHANGE_OTHER_TABLE_ERROR_MESSAGE);

        table.setStatus(status);
        table.setLastUpdate(Timestamp.valueOf(LocalDateTime.now()));
    }
}
