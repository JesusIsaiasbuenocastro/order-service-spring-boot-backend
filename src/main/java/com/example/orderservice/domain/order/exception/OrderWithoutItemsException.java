package com.example.orderservice.domain.order.exception;

public class OrderWithoutItemsException extends RuntimeException {

    public String codeError;

    public OrderWithoutItemsException(String codeError, String mensage) {
        super(mensage);
        this.codeError = codeError;
    }
    public String getCode(){
        return codeError;
    }

}