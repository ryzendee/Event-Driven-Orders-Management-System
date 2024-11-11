package com.ryzendee.kafka.models.events.product;

import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
public class ProductReservedEvent {
    private final UUID orderId;
    private final UUID productId;
    private final BigDecimal price;
    private final Integer quantity;
}
