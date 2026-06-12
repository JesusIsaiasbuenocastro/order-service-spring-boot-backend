package com.example.orderservice.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customerId;
    private BigDecimal amount;
    private String status;
    private LocalDateTime createdAt;

    //  Sin orphanRemoval: si la lista de items cambia (o se reemplaza),
    // los items viejos quedan huérfanos en la BD sin dueño — filas basura.
    // Con orphanRemoval: JPA borra automáticamente cualquier OrderItemEntity
    // que ya no esté referenciada por este OrderEntity.
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItemEntity> items;
}