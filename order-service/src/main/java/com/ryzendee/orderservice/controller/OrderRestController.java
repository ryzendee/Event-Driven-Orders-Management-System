package com.ryzendee.orderservice.controller;

import com.ryzendee.orderservice.dto.request.CreateOrderRequest;
import com.ryzendee.orderservice.dto.response.OrderResponse;
import com.ryzendee.orderservice.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderRestController {

    private final OrderService orderService;

    @PostMapping
    public OrderResponse createOrder(@Valid CreateOrderRequest request) {
        return orderService.createOrder(request);
    }
}
