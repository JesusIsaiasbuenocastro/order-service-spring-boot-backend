package com.example.orderservice.interfaces.rest.exception.handler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.orderservice.application.order.exception.OrderNotFoundException;
import com.example.orderservice.domain.order.exception.InvalidOrderStateException;
import com.example.orderservice.domain.order.exception.OrderAlreadyConfirmedException;
import com.example.orderservice.domain.order.exception.OrderWithoutItemsException;
import com.example.orderservice.infrastructure.exception.PersistenceException;
import com.example.orderservice.interfaces.rest.exception.dto.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
     
    @ExceptionHandler(PersistenceException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(PersistenceException ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(OrderNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(InvalidOrderStateException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(InvalidOrderStateException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(OrderAlreadyConfirmedException.class)
    public ResponseEntity<ErrorResponse> handleAlreadyConfirmed(OrderAlreadyConfirmedException ex) {
        return buildResponse(HttpStatus.CONFLICT, ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(OrderWithoutItemsException.class)
    public ResponseEntity<ErrorResponse> handleWithoutItems(OrderWithoutItemsException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_ERROR",
                "An unexpected error has occurred");
    }

    private ResponseEntity<ErrorResponse> buildResponse(HttpStatus status, String code, String message) {
        return ResponseEntity
                .status(status)
                .body(new ErrorResponse(code, message, LocalDateTime.now()));
    }
}