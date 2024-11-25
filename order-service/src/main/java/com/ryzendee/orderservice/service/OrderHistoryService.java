package com.ryzendee.orderservice.service;

import com.ryzendee.orderservice.entity.OrderEntity;
import com.ryzendee.orderservice.enums.OrderStatus;

import java.util.UUID;

public interface OrderHistoryService {

    void addOrder(UUID orderId, OrderStatus status);
}
