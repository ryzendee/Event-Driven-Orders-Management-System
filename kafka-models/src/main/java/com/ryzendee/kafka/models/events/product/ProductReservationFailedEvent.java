package com.ryzendee.kafka.models.events.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class ProductReservationFailedEvent {
    private final UUID orderId;
    private final UUID productId;
    private final Integer productQuantity;
}
