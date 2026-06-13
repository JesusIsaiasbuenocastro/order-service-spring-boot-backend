package com.example.orderservice.interfaces.rest.dto;

import java.util.List;

import lombok.Data;

@Data
public class OrderDto {
    public Long orderId;
    public Long customerId;
    public String status;
    public List<OrderItemDto>  items;
}
