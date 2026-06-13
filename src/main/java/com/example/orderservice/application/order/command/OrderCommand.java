package com.example.orderservice.application.order.command;

import java.util.List;

import lombok.Builder;

@Builder
public record OrderCommand(Long orderId,Long customerId, String status,List<OrderItemCommand> items) 
{
}

