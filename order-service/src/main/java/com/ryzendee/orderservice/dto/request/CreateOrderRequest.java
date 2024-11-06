package com.ryzendee.orderservice.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateOrderRequest(
        @NotNull
        UUID customerId,
        @NotNull
        UUID productId,
        @NotNull
        Integer quantity
) {
}
