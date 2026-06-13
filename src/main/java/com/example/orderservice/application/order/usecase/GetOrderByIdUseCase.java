package com.example.orderservice.application.order.usecase;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.orderservice.application.order.command.OrderItemCommand;
import com.example.orderservice.application.order.exception.OrderNotFoundException;
import com.example.orderservice.application.order.response.GetOrderResponse;
import com.example.orderservice.domain.order.model.Order;
import com.example.orderservice.domain.order.repository.OrderRepository;

@Service
public class GetOrderByIdUseCase {

    private final OrderRepository orderRepository;

    public GetOrderByIdUseCase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public GetOrderResponse GetOrderById(Long orderId) {

        Optional<Order> order = orderRepository.findById(orderId);

        if (order.isEmpty()){
            throw new OrderNotFoundException("NOT_FOUND","Order does not exists");
        }

        return GetOrderResponse.builder()
            .orderId(order.get().getId())
            .customerId(order.get().getCustomerId())
            .status(order.get().getStatus().name())
            .items(
                order.get().getItems().stream()
                    .map(i -> new OrderItemCommand(
                        i.getProductId(),
                        i.getQuantity(),
                        i.getPrice()
                    ))
                    .toList()
            )
            .build();

    }

      
}