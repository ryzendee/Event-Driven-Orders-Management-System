package com.ryzendee.kafka.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class CreateOrderEvent {

    private final UUID orderId;
    private final UUID customerId;
    private final UUID productId;
    private final Integer productQuantity;
}
