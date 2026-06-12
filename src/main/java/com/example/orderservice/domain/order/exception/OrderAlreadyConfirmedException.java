package com.example.orderservice.domain.order.exception;

public class OrderAlreadyConfirmedException extends RuntimeException {

    private final String code;

    public OrderAlreadyConfirmedException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}