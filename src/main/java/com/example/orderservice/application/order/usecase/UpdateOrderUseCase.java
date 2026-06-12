package com.example.orderservice.application.order.usecase;

import org.springframework.stereotype.Service;

import com.example.orderservice.application.order.command.UpdateOrderCommand;
import com.example.orderservice.application.order.exception.OrderNotFoundException;
import com.example.orderservice.application.order.response.UpdateOrderResult;
import com.example.orderservice.domain.order.exception.InvalidOrderStateException;
import com.example.orderservice.domain.order.model.Order;
import com.example.orderservice.domain.order.model.OrderStatus;
import com.example.orderservice.domain.order.repository.OrderRepository;

@Service
public class UpdateOrderUseCase {

    private final OrderRepository orderRepository;


    public UpdateOrderUseCase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

     public UpdateOrderResult execute(UpdateOrderCommand command) {

        Order order = orderRepository.findById(command.orderId())
            .orElseThrow(() -> new OrderNotFoundException("NOT_FOUND","Order not found"));

        OrderStatus newStatus = updateStatus(command.status());
 
        //    El dominio (Order + OrderStatus) decide si la transición es válida
        //    y lanza InvalidOrderStateException si no lo es.
        switch (newStatus) {
            case CONFIRMED -> order.confirm();
            case CANCELED  -> order.cancel();
            case PENDING   -> order.markAsPending();
            default -> 
                throw new InvalidOrderStateException("UNSUPPORTED_TRANSITION","Transition to " + newStatus + " not supported");
        }
        
        orderRepository.updateStatus(order.getId(), newStatus);

        // Responder con un mapper
        return new UpdateOrderResult(
            order.getId(),
            order.getStatus().name()
        );
    }

     public OrderStatus updateStatus(String status) {

        try {
            return OrderStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new com.example.orderservice.domain.order.exception.InvalidOrderStateException(
                    "INVALID_STATUS",
                    "Unknown order status: '" + status + "'. Valid values: CONFIRMED, CANCELED, PENDING"
            );
        }
    }


}