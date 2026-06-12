package com.example.orderservice.interfaces.rest.mapper;

import org.springframework.stereotype.Component;

import com.example.orderservice.application.order.response.GetOrderResponse;
import com.example.orderservice.interfaces.rest.dto.GetOrderResponseDto;
import com.example.orderservice.interfaces.rest.dto.OrderItemDto;

@Component
public class GetOrderMapperResponse {

    public static GetOrderResponseDto toResponse(GetOrderResponse getOrderResponse) {

        return new GetOrderResponseDto(
            getOrderResponse.orderId(),
            getOrderResponse.customerId(),
            getOrderResponse.items().stream()
                .map(i -> new OrderItemDto(
                    i.productId(),
                    i.quantity(),
                    i.price()
                )
            ).toList()
        );
    }
}
