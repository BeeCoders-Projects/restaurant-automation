package com.beecoders.ras.model.constants;

public enum PaymentType {
    CASH("Зачекайте, будь ласка, офіціант підійде до\nвас, щоб прийняти оплату"),
    DEBIT("Оплата пройшла успішно.\nДякуємо та чекаємо вас\nнаступного разу");

    private final String successfulMessage;
    PaymentType(String successfulMessage) {
        this.successfulMessage = successfulMessage;
    }

    public String getSuccessfulMessage() {
        return successfulMessage;
    }
}
