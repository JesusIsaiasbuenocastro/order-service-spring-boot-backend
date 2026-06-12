package com.example.orderservice.application.order.command;

import java.util.List;

import lombok.Builder;

@Builder
public record GetOrdersResult (
    List<OrderCommand> orders
){}
