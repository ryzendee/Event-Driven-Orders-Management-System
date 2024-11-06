package com.ryzendee.orderservice.dto.response;

import com.ryzendee.orderservice.enums.OrderStatus;

import java.util.UUID;

public record OrderResponse(
        UUID orderId,
        UUID customerId,
        UUID productId,
        Integer quantity,
        OrderStatus status
) {
}
