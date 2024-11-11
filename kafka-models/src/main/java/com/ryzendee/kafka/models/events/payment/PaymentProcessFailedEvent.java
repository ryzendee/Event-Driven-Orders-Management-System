package com.ryzendee.kafka.models.events.payment;


import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class PaymentProcessFailedEvent {
    private final UUID orderId;
    private final UUID productId;
    private final Integer productQuantity;
}
