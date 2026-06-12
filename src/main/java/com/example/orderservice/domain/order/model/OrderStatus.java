package com.example.orderservice.domain.order.model;

public enum OrderStatus {
    
    CREATED {
        @Override
        public boolean canTransitionTo(OrderStatus next) {
            return next == CONFIRMED || next == PENDING || next == CANCELED;
        }
    },
    CONFIRMED {
        @Override
        public boolean canTransitionTo(OrderStatus next) {
            // Una orden confirmada ya no puede volver a CREATED ni cancelarse fácilmente
            return false;
        }
    },
    PENDING {
        @Override
        public boolean canTransitionTo(OrderStatus next) {
            return next == CONFIRMED || next == CANCELED;
        }
    },
    CANCELED {
        @Override
        public boolean canTransitionTo(OrderStatus next) {
            // Orden cancelada: estado terminal, no se puede mover
            return false;
        }
    };

    //Clase abstracta para verificar la secuencia de estatus
    public abstract boolean canTransitionTo(OrderStatus next);
}
