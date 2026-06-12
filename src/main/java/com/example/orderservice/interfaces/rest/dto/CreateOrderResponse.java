package com.example.orderservice.interfaces.rest.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CreateOrderResponse {
    public Long orderId;
    public String status;
    public BigDecimal total;
    
}