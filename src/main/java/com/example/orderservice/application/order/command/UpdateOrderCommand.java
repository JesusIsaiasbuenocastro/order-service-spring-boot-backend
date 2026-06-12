package com.example.orderservice.application.order.command;

import lombok.Builder;

@Builder
public record UpdateOrderCommand (Long orderId,String status) 
{
}
