package com.ryzendee.kafka.models.commands;

import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class CreateShipmentCommand {
    private final UUID orderId;
}
