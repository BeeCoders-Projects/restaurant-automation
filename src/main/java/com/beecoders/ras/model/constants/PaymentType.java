package com.beecoders.ras.model.constants;

public enum PaymentType {
    CASH("Just wait your waiter"),
    DEBIT("Payment was successful");

    private final String successfulMessage;
    PaymentType(String successfulMessage) {
        this.successfulMessage = successfulMessage;
    }

    public String getSuccessfulMessage() {
        return successfulMessage;
    }
}
