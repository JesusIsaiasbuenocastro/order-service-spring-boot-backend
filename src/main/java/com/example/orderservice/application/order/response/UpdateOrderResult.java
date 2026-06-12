package com.example.orderservice.application.order.response;

import lombok.Builder;

@Builder
public record UpdateOrderResult (
    Long orderId,
    String status
) {
}