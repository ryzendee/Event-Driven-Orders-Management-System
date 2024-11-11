package com.ryzendee.kafka.models.commands;

import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
public class ProcessPaymentCommand {
    private final UUID orderId;
    private final UUID productId;
    private final BigDecimal productPrice;
    private final Integer productQuantity;
}
