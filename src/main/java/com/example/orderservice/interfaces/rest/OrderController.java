package com.example.orderservice.interfaces.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.orderservice.application.order.command.CreateOrderCommand;
import com.example.orderservice.application.order.command.CreateOrderResult;
import com.example.orderservice.application.order.command.GetOrdersResult;
import com.example.orderservice.application.order.command.UpdateOrderCommand;
import com.example.orderservice.application.order.response.GetOrderResponse;
import com.example.orderservice.application.order.response.UpdateOrderResult;
import com.example.orderservice.application.order.usecase.CreateOrderUseCase;
import com.example.orderservice.application.order.usecase.GetAllOrdersUseCase;
import com.example.orderservice.application.order.usecase.GetOrderByIdUseCase;
import com.example.orderservice.application.order.usecase.UpdateOrderUseCase;
import com.example.orderservice.interfaces.rest.dto.CreateOrderRequest;
import com.example.orderservice.interfaces.rest.dto.CreateOrderResponse;
import com.example.orderservice.interfaces.rest.dto.GetOrderResponseDto;
import com.example.orderservice.interfaces.rest.dto.GetOrdersResponse;
import com.example.orderservice.interfaces.rest.dto.UpdateOrderRequest;
import com.example.orderservice.interfaces.rest.dto.UpdateOrderResponse;
import com.example.orderservice.interfaces.rest.mapper.CreateOrderMapperRequest;
import com.example.orderservice.interfaces.rest.mapper.CreateOrderMapperResponse;
import com.example.orderservice.interfaces.rest.mapper.GetOrderMapperResponse;
import com.example.orderservice.interfaces.rest.mapper.GetOrdersMapperResponse;
import com.example.orderservice.interfaces.rest.mapper.UpdateOrderMapperRequest;
import com.example.orderservice.interfaces.rest.mapper.UpdateOrderMapperResponse;

import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/orders")
@Tag(name = "Orders", description = "Operaciones de órdenes")
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;
    private final GetAllOrdersUseCase getAllOrdersUseCase;
    private final GetOrderByIdUseCase getOrderByIdUseCase;
    private final UpdateOrderUseCase updateOrderUseCase;

    public OrderController(CreateOrderUseCase createOrderUseCase, GetAllOrdersUseCase getAllOrdersUseCase, GetOrderByIdUseCase getOrderByIdUseCase,
        UpdateOrderUseCase updateOrderUseCase
    ) {
        this.createOrderUseCase = createOrderUseCase;
        this.getAllOrdersUseCase = getAllOrdersUseCase;
        this.getOrderByIdUseCase = getOrderByIdUseCase;
        this.updateOrderUseCase = updateOrderUseCase;
    }

    @PostMapping
    public ResponseEntity<CreateOrderResponse> create(@RequestBody CreateOrderRequest request) {
        
        CreateOrderCommand command = (CreateOrderCommand)CreateOrderMapperRequest.toCommand(request);
        CreateOrderResult createOrderResult = createOrderUseCase.execute(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(CreateOrderMapperResponse.toResponse(createOrderResult));
    }

    @GetMapping
    public ResponseEntity<GetOrdersResponse> getAll() {
        
        GetOrdersResult getOrders = getAllOrdersUseCase.GetAllOrders();
        return ResponseEntity.status(HttpStatus.OK).body(GetOrdersMapperResponse.toResponse(getOrders));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetOrderResponseDto>  getById(@PathVariable Long id) {
        GetOrderResponse getOrderResponse = getOrderByIdUseCase.GetOrderById(id);
        return ResponseEntity.status(HttpStatus.OK).body(GetOrderMapperResponse.toResponse(getOrderResponse));
    }


    @PutMapping("/{id}")
    public ResponseEntity<UpdateOrderResponse>  update(@PathVariable Long id, @RequestBody UpdateOrderRequest updateOrderRequest) {
        
        UpdateOrderCommand command = UpdateOrderMapperRequest.toCommand(id,updateOrderRequest);
        UpdateOrderResult updateOrderResponse = (UpdateOrderResult) updateOrderUseCase.execute(command);
        return ResponseEntity.status(HttpStatus.OK).body(UpdateOrderMapperResponse.toResponse(updateOrderResponse));

    }
    
}