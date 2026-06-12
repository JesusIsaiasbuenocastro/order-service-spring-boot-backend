package com.example.orderservice.interfaces.rest.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.orderservice.application.order.command.GetOrdersResult;
import com.example.orderservice.application.order.command.OrderCommand;
import com.example.orderservice.application.order.command.OrderItemCommand;
import com.example.orderservice.interfaces.rest.dto.GetOrdersResponse;
import com.example.orderservice.interfaces.rest.dto.OrderDto;
import com.example.orderservice.interfaces.rest.dto.OrderItemDto;

@Component
public class GetOrdersMapperResponse {
     public static GetOrdersResponse toResponse(GetOrdersResult getOrdersResult) {

        List<OrderDto> orders = getOrdersResult.orders().stream()
            .map(GetOrdersMapperResponse::toDto)
            .toList();

        return GetOrdersResponse.builder()
            .orders(orders)
            .build();
    }

   public static OrderDto toDto(OrderCommand command) {

        OrderDto dto = new OrderDto();
        dto.setOrderId(command.orderId());
        dto.setCustomerId(command.customerId());
        dto.setItems(toItemDtos(command.items())); 

        return dto;
    }
    
    private static List<OrderItemDto> toItemDtos(List<OrderItemCommand> items) {
        return items.stream()
            .map(GetOrdersMapperResponse::toItemDto)
            .toList();
    }

    private static OrderItemDto toItemDto(OrderItemCommand item) {
        return new OrderItemDto(
            item.productId(),
            item.quantity(),
            item.price()
        );
    }
}
