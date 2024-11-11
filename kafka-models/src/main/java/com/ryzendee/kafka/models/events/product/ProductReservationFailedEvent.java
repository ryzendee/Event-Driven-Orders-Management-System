package com.ryzendee.kafka.models.events.product;

import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class ProductReservationFailedEvent {
    private final UUID orderId;
    private final UUID productId;
    private final Integer productQuantity;
}
