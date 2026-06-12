package com.example.orderservice.application.order.command;

import java.math.BigDecimal;

import lombok.Builder;

@Builder
public record CreateOrderResult(
    Long orderId,
    BigDecimal total,
    String status
) {
}