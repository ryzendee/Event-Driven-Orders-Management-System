package com.ryzendee.kafka.models.events.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class OrderCreatedEvent {
    private final UUID orderId;
    private final UUID customerId;
    private final UUID productId;
    private final Integer productQuantity;
}
