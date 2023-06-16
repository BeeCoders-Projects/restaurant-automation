package com.beecoders.ras.model.constants;

public class OrderConstant {
    public static final double CALC_PERCENT = 100D;

    public static final String NO_CARD_DEBIT_TYPE_ERROR_MESSAGE = "Card should be present for DEBIT type payment";
    public static final String ORDER_NOT_FOUND_ERROR_MESSAGE = "Order with id (%s) not found";
    public static final String ORDER_ALREADY_PAID_ERROR_MESSAGE = "Order with id (%s) already paid";
    public static final String PROMOCODE_NOT_FOUND_ERROR_MESSAGE = "Promocode [%s] not found";
    public static final String SOME_DISHES_NOT_FOUND_ERROR_MESSAGE = "One or more of the dishes in the order does not exist";
    public static final String DUBLICATE_DISHES_REQUEST_ERROR_MESSAGE = "Some dishes have duplicate in the request";
    public static final String EXPIRED_PROMOCODE_ERROR_MESSAGE = "Promocode is expired";
    public static final String ALREADY_USED_PROMOCODE_ERROR_MESSAGE = "Order already used promocode";
}
