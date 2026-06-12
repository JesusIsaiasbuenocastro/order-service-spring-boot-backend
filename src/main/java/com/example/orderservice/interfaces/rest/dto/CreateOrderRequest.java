package com.example.orderservice.interfaces.rest.dto;

import java.util.List;

import lombok.Data;

@Data
public class CreateOrderRequest {
    public Long customerId;
    public List<OrderItemDto>  items;
}