package com.beecoders.ras.exception.s3;

public class EmptyImageException extends RuntimeException {
    public EmptyImageException(String message) {
        super(message);
    }
}
