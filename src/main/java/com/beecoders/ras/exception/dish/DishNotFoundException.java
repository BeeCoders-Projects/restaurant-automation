package com.beecoders.ras.exception.dish;

import jakarta.persistence.PersistenceException;

public class DishNotFoundException extends PersistenceException {
    public DishNotFoundException(String message) {
        super(message);
    }
}
