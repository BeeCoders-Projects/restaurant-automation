package com.beecoders.ras.exception.s3;

public class IncorrectImageFormatException extends RuntimeException {
    public IncorrectImageFormatException(String message) {
        super(message);
    }
}
