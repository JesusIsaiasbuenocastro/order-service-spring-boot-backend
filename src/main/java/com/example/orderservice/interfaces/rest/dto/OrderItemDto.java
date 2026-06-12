package com.example.orderservice.interfaces.rest.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class OrderItemDto {
    public Long productId;
    public int quantity;
    public BigDecimal price;

    public OrderItemDto(Long productId, int quantity, BigDecimal price) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }
    
    
}
