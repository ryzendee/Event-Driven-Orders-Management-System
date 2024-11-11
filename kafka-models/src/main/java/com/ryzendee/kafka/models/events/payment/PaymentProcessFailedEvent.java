package com.ryzendee.kafka.models.events.payment;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class PaymentProcessFailedEvent {
    private final UUID orderId;
    private final UUID productId;
    private final BigDecimal productPrice;
    private final Integer productQuantity;
}
