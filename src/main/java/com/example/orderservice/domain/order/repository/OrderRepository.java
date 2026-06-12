package com.example.orderservice.domain.order.repository;

import java.util.List;
import java.util.Optional;

import com.example.orderservice.domain.order.model.Order;
import com.example.orderservice.domain.order.model.OrderStatus;

public interface OrderRepository {
    Order save(Order order);
    Optional<Order> findById(Long id);
    List<Order> findByAll();
    void updateStatus(Long orderId, OrderStatus newStatus);
}