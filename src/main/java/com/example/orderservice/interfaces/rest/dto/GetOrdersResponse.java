package com.example.orderservice.interfaces.rest.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GetOrdersResponse {
    private List<OrderDto> orders;
}
