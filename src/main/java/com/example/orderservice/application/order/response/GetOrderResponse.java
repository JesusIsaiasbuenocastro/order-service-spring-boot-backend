package com.example.orderservice.application.order.response;

import java.util.List;

import com.example.orderservice.application.order.command.OrderItemCommand;

import lombok.Builder;

@Builder
public record GetOrderResponse (Long orderId, Long customerId, String status,List<OrderItemCommand> items) 
{
}
