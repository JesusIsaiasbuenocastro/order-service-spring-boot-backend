package com.example.orderservice.interfaces.rest.mapper;

import org.springframework.stereotype.Component;

import com.example.orderservice.application.order.command.UpdateOrderCommand;
import com.example.orderservice.interfaces.rest.dto.UpdateOrderRequest;

@Component
public class UpdateOrderMapperRequest {
     public static UpdateOrderCommand toCommand(Long orderId,UpdateOrderRequest request){
        return new UpdateOrderCommand(
            orderId,
            request.getStatus()
        );
    }
}
