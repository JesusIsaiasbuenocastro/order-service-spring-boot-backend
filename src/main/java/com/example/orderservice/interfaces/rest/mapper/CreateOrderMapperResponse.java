package com.example.orderservice.interfaces.rest.mapper;

import org.springframework.stereotype.Component;

import com.example.orderservice.application.order.command.CreateOrderResult;
import com.example.orderservice.interfaces.rest.dto.CreateOrderResponse;

@Component
public class CreateOrderMapperResponse {

    public static CreateOrderResponse toResponse(CreateOrderResult createOrderResult) {
        return CreateOrderResponse.builder()
                .orderId(createOrderResult.orderId())
                .status("CREATED")
                .total(createOrderResult.total())
                .build();
    }
}
