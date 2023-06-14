package com.beecoders.ras.exception.order;

public class IllegalPaymentException extends RuntimeException {
    public IllegalPaymentException(String message) {
        super(message);
    }
}
