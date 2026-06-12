package com.example.orderservice.application.order.usecase;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.orderservice.application.order.command.CreateOrderCommand;
import com.example.orderservice.application.order.command.CreateOrderResult;
import com.example.orderservice.domain.order.model.Order;
import com.example.orderservice.domain.order.model.OrderItem;
import com.example.orderservice.domain.order.repository.OrderRepository;

@Service
public class CreateOrderUseCase {

    private final OrderRepository orderRepository;

     public CreateOrderUseCase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public CreateOrderResult execute(CreateOrderCommand command) {

        // Mapear command → domain (AQUÍ VA LA LÓGICA REAL)
        List<OrderItem> items = command.items().stream().<OrderItem>
            map(i -> new OrderItem(
                i.productId(),
                i.quantity(),
                i.price() // simulado o servicio
            ))
            .toList();

        Order order = new Order(
            command.customerId(),
            items
        );

        // Persistir (infraestructura)
        Order savedOrder = orderRepository.save(order);

        // Responder con un mapper
        return CreateOrderResult.builder()
            .orderId(savedOrder.getId())
            .total(savedOrder.getTotal())
            .status("CREATED").build();
    }

}