package com.example.orderservice.application.order.command;

import java.util.List;

import lombok.Builder;

@Builder
public record CreateOrderCommand(Long customerId, List<OrderItemCommand> items) 
{
}
