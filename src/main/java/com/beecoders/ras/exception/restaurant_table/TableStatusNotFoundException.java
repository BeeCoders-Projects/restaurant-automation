package com.beecoders.ras.exception.restaurant_table;

public class TableStatusNotFoundException extends RuntimeException {
    public TableStatusNotFoundException(String message) {
        super(message);
    }
}
