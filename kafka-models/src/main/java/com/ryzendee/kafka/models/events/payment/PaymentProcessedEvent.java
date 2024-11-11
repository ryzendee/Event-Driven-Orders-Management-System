package com.ryzendee.kafka.models.events.payment;

import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class PaymentProcessedEvent {
    private final UUID orderId;
    private final UUID paymentId;
}
