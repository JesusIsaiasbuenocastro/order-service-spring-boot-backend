package com.example.orderservice.interfaces.rest.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UpdateOrderResponse {
    public Long orderId;
    public String status;
    public LocalDateTime updateDate; 

    public UpdateOrderResponse (Long orderId,String status,LocalDateTime updateDate){
        this.orderId = orderId;
        this.status = status;
        this.updateDate = updateDate;
    }
}
