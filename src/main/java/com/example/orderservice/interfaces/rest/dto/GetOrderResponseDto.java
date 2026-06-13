package com.example.orderservice.interfaces.rest.dto;

import java.util.List;

import lombok.Data;

@Data
public class GetOrderResponseDto {
    public Long customerId;
    public Long orderId;
    public String status;
    public List<OrderItemDto> items;

    public GetOrderResponseDto(Long orderId, Long customerId, String status,List<OrderItemDto> items ){
        this.orderId = orderId;
        this.customerId = customerId;
        this.status = status;
        this.items = items;
    }

}
