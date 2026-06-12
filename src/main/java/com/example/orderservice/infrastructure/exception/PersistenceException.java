package com.example.orderservice.infrastructure.exception;

public class PersistenceException extends RuntimeException {

    public final String codeError;


    public PersistenceException(String codeError, String mensage) {
        super(mensage);
        this.codeError = codeError;
    }

    public String getCode() {
        return codeError;
    }
}