package com.example.orderservice.interfaces.rest.mapper;


import org.springframework.stereotype.Component;

import com.example.orderservice.application.order.command.CreateOrderCommand;
import com.example.orderservice.application.order.command.OrderItemCommand;
import com.example.orderservice.interfaces.rest.dto.CreateOrderRequest;

@Component
public class CreateOrderMapperRequest {
    public static CreateOrderCommand toCommand(CreateOrderRequest request){
        return new CreateOrderCommand(
            request.getCustomerId(),
            request.getItems().stream().<OrderItemCommand>map(
                i -> new OrderItemCommand(
                    i.productId,
                    i.quantity,
                    i.price
                )
            ).toList()
        );
    }
}