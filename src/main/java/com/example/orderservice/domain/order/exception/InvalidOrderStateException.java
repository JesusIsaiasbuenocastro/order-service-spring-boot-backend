package com.example.orderservice.domain.order.exception;

public class InvalidOrderStateException extends RuntimeException {

    public String codeError;

    public InvalidOrderStateException(String codeError, String mensage) {
        super(mensage);
        this.codeError = codeError;
    }

    public String getCode(){
        return this.codeError;
    }
}