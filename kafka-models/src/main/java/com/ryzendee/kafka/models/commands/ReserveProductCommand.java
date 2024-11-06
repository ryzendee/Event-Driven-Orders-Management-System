package com.ryzendee.kafka.models.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class ReserveProductCommand {

    private final UUID orderId;
    private final UUID productId;
    private final Integer quantity;
}
