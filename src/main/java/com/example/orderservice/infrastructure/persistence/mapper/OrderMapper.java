package com.example.orderservice.infrastructure.persistence.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.orderservice.domain.order.model.Order;
import com.example.orderservice.domain.order.model.OrderItem;
import com.example.orderservice.domain.order.model.OrderStatus;
import com.example.orderservice.infrastructure.persistence.entity.OrderEntity;
import com.example.orderservice.infrastructure.persistence.entity.OrderItemEntity;

@Component
public class OrderMapper {
    
    public OrderEntity toEntity(Order order){
        OrderEntity entity = new OrderEntity();
        entity.setId(order.getId());
        entity.setCustomerId(order.getCustomerId());
        entity.setAmount(order.getTotal());
        entity.setStatus(order.getStatus().name());
        entity.setCreatedAt(order.getCreatedAt());

        List<OrderItemEntity> items = order.getItems().stream()
        .map(item -> {
            OrderItemEntity itemEntity = new OrderItemEntity();
            itemEntity.setProductId(item.getProductId());
            itemEntity.setQuantity(item.getQuantity());
            itemEntity.setPrice(item.getPrice());
            itemEntity.setOrder(entity);
            return itemEntity;
        })
        .toList();
        entity.setItems(items);
        return entity;
    }

    public List<Order> getAlltoDomain(List<OrderEntity> ordersEntity){

        List<Order>  Orders;
        Orders = ordersEntity.isEmpty()
                ? List.of()
                : ordersEntity.stream()
                    .map(this::toDomain)
                    .toList();

        return Orders;
    }

    public Order toDomain(OrderEntity orderEntity){

        if (orderEntity == null) return null;
        
        List<OrderItem> items = orderEntity.getItems() == null
                ? List.of()
                : orderEntity.getItems().stream()
                    .map(this::toItemDomain)
                    .toList();

         // seteo controlado (porque ya viene persistido)
        // solo reconstruye el objeto tal como estaba en BD
       return Order.reconstitute(
                orderEntity.getId(),
                orderEntity.getCustomerId(),
                items,
                mapStatus(orderEntity.getStatus()),
                orderEntity.getCreatedAt(),
                orderEntity.getAmount()
        );
    }

     private OrderItem toItemDomain(OrderItemEntity entity) {
        return new OrderItem(
                entity.getProductId(),
                entity.getQuantity(),
                entity.getPrice()
        );
    }

    private OrderStatus mapStatus(String status) {
        return status == null
                ? null
                : OrderStatus.valueOf(status);
    }


}