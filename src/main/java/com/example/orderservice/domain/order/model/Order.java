package com.example.orderservice.domain.order.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import com.example.orderservice.domain.order.exception.InvalidOrderStateException;
import com.example.orderservice.domain.order.exception.OrderAlreadyConfirmedException;
import com.example.orderservice.domain.order.exception.OrderWithoutItemsException;

public class Order {
    private Long id;
    private List<OrderItem> items;
    private OrderStatus status;
    private Long customerId;
    private LocalDateTime createdAt;
    private BigDecimal total;

    public Order(Long customerId, List<OrderItem> items) {
        if (items == null || items.isEmpty()) {
            throw new OrderWithoutItemsException("INVALID_ORDER","Order must have at least one item");
        }

        this.customerId = customerId;
        this.items = items;
        this.status = OrderStatus.CREATED;
        this.createdAt = LocalDateTime.now();
        this.total = calculateTotal();
    }

    private Order(Long id, Long customerId, List<OrderItem> items,
                  OrderStatus status, LocalDateTime createdAt, BigDecimal total) {
        this.id = id;
        this.customerId = customerId;
        this.items = items;
        this.status = status;
        this.createdAt = createdAt;
        this.total = total;
    }

    public static Order reconstitute(Long id, Long customerId, List<OrderItem> items,
                                     OrderStatus status, LocalDateTime createdAt, BigDecimal total) {
        return new Order(id, customerId, items, status, createdAt, total);
    }

    //Logica de negocio 
     public void confirm() {
        if (this.status == OrderStatus.CANCELED) {
            throw new InvalidOrderStateException("ILLEGAL_STATUS", "Cannot confirm a cancelled order");
        }
        if (this.status == OrderStatus.CONFIRMED) {
            throw new OrderAlreadyConfirmedException("ALREADY_CONFIRMED", "Order is already confirmed");
        }
        this.status = OrderStatus.CONFIRMED;
    }

    public void markAsPending() {
        if (this.status == OrderStatus.CANCELED) {
            throw new InvalidOrderStateException("ILLEGAL_STATUS", "Cannot reopen a cancelled order");
        }
        this.status = OrderStatus.PENDING;
    }

     public void cancel() {
        if (this.status == OrderStatus.CONFIRMED) {
            throw new InvalidOrderStateException("ILLEGAL_STATUS", "Cannot cancel a confirmed order");
        }
        if (this.status == OrderStatus.CANCELED) {
            throw new InvalidOrderStateException("ILLEGAL_STATUS", "Order is already cancelled");
        }
        this.status = OrderStatus.CANCELED;
    }

    private BigDecimal calculateTotal() {
        return items.stream()
                .map(OrderItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // GETTERS 

    public Long getId() { return id; }
    public Long getCustomerId() { return customerId; }
    public List<OrderItem> getItems() { return Collections.unmodifiableList(items); }
    public BigDecimal getTotal() { return total; }
    public OrderStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    // Solo el id puede ser asignado externamente (lo da la BD tras persistir)
    public void setId(Long id) { this.id = id; }

    

}