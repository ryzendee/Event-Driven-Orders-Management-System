package com.ryzendee.orderservice.service;

import com.ryzendee.orderservice.dto.request.CreateOrderRequest;
import com.ryzendee.orderservice.dto.response.OrderResponse;

import java.util.UUID;

public interface OrderService {

    OrderResponse createOrder(CreateOrderRequest request);
    void approveOrder(UUID orderId);
    void rejectOrder(UUID orderId);
}
