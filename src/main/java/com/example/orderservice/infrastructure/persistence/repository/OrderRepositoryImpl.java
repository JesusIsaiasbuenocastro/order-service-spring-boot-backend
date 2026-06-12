package com.example.orderservice.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.orderservice.domain.order.model.Order;
import com.example.orderservice.domain.order.model.OrderStatus;
import com.example.orderservice.domain.order.repository.OrderRepository;
import com.example.orderservice.infrastructure.exception.PersistenceException;
import com.example.orderservice.infrastructure.persistence.entity.OrderEntity;
import com.example.orderservice.infrastructure.persistence.mapper.OrderMapper;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class OrderRepositoryImpl  implements  OrderRepository{
    
    private final JpaOrderRepository jpaRepository;
    private final OrderMapper mapper;

    @Override
    public Order save(Order order) {
        
        try {
            OrderEntity entity = mapper.toEntity(order);
            OrderEntity saved = jpaRepository.save(entity);  
            return mapper.toDomain(saved);
        } catch (Exception e) {
            throw new PersistenceException("DB_ERROR", "Error accessing database");
        }
    }

    @Override
    public Optional<Order> findById(Long id) {
        
        try {
           return jpaRepository.findById(id)
                    .map(mapper::toDomain);
        } catch (Exception e) {
            throw new PersistenceException("DB_ERROR", "Error accessing database");
        } 
    }

    @Override
    public List<Order> findByAll() {
         try {
            List<OrderEntity>  ordersEntity = jpaRepository.findAll();
            return mapper.getAlltoDomain(ordersEntity);
        } catch (Exception e) {
            throw new PersistenceException("DB_ERROR", "Error accessing database");
        }
    }

    /* @Transactional es obligatorio en metodos @Modifying -- sin el, Spring lanza
     TransactionRequiredException en tiempo de ejecucion.*/ 
    @Override
    @Transactional
    public void updateStatus(Long orderId, OrderStatus newStatus) {
        try {
            int updated = jpaRepository.updateStatus(orderId, newStatus.name());
            if (updated == 0) {
                throw new PersistenceException("NOT_FOUND", "Order not found during status update");
            }
        } catch (PersistenceException e) {
            throw e;
        } catch (Exception e) {
            throw new PersistenceException("DB_ERROR", "Error updating order status: " + e.getMessage());
        }
    }
    
}
