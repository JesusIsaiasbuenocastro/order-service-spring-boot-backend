package com.example.orderservice.application.order.command;

import java.math.BigDecimal;

import lombok.Builder;

@Builder
public record OrderItemCommand(Long productId, int quantity, BigDecimal price) {

    public boolean isValid() {
        return quantity > 0;
    }
}
