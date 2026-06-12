package com.example.orderservice.interfaces.rest.exception.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {
     private String code;
    private String message;
    private LocalDateTime timestamp;


    public ErrorResponse(String code, String message,LocalDateTime timestamp) {
        this.code = code;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}