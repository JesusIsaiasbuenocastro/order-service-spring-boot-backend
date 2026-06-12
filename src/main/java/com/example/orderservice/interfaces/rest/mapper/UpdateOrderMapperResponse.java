package com.example.orderservice.interfaces.rest.mapper;

import java.time.LocalDateTime;

import com.example.orderservice.application.order.response.UpdateOrderResult;
import com.example.orderservice.interfaces.rest.dto.UpdateOrderResponse;

public class UpdateOrderMapperResponse {

    public static UpdateOrderResponse toResponse(UpdateOrderResult updateOrderResult) {
        return UpdateOrderResponse.builder()
                .orderId(updateOrderResult.orderId())
                .status(updateOrderResult.status())
                .updateDate(LocalDateTime.now())
                .build();
    }
}
