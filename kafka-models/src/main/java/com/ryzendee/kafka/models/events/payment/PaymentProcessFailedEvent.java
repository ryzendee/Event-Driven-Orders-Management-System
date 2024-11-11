package com.ryzendee.kafka.models.events.payment;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class PaymentProcessFailedEvent {
    private final UUID orderId;
    private final UUID productId;
    private final Integer productQuantity;
}
