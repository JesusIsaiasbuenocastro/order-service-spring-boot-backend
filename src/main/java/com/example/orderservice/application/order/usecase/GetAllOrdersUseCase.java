package com.example.orderservice.application.order.usecase;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.orderservice.application.order.command.GetOrdersResult;
import com.example.orderservice.application.order.command.OrderCommand;
import com.example.orderservice.application.order.command.OrderItemCommand;
import com.example.orderservice.domain.order.model.Order;
import com.example.orderservice.domain.order.model.OrderItem;
import com.example.orderservice.domain.order.repository.OrderRepository;

@Service
public class GetAllOrdersUseCase {
    private final OrderRepository orderRepository;

     public GetAllOrdersUseCase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public GetOrdersResult GetAllOrders() {
          // 1. Obtener órdenes del repositorio (dominio)
        List<Order> orders = orderRepository.findByAll();

        // 2. Convertir Order → OrderCommand (Application layer)
        List<OrderCommand> orderCommands = orders.stream()
            .map(this::toCommand)
            .toList();

        // 3. Retornar resultado
        return GetOrdersResult.builder()
            .orders(orderCommands)
            .build();
    }

    // Mapper interno (Domain → Application)
    private OrderCommand toCommand(Order order) {
        return OrderCommand.builder()
            .orderId(order.getId())
            .customerId(order.getCustomerId())
            .items(toItemCommands(order.getItems())).build();
    }

    private List<OrderItemCommand> toItemCommands(List<OrderItem> items) {
        return items.stream()
        .map(this::toItemCommand)
        .toList();
    }

    private OrderItemCommand toItemCommand(OrderItem item) {
        return OrderItemCommand.builder()
            .productId(item.getProductId())
            .quantity(item.getQuantity())
            .price(item.getPrice())
            .build();
    }

}