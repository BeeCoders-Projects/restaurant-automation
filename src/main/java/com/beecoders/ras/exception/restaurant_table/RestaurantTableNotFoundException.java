package com.beecoders.ras.exception.restaurant_table;

public class RestaurantTableNotFoundException extends RuntimeException {
    public RestaurantTableNotFoundException(String message) {
        super(message);
    }
}
