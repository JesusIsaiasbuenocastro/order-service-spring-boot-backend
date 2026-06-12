package com.example.orderservice.application.order.exception;

public class OrderNotFoundException extends RuntimeException {
    
    public final String codeError;

    public OrderNotFoundException (String codeError,String message){
        super(message);
        this.codeError = codeError;
    }

    public String getCode() {
        return codeError;
    }
}