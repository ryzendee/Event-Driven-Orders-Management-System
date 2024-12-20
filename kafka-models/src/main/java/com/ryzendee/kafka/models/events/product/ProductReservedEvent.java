package com.ryzendee.kafka.models.events.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class ProductReservedEvent {
    private final UUID orderId;
    private final UUID productId;
    private final BigDecimal price;
    private final Integer quantity;
}
