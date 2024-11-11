package com.ryzendee.kafka.models.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class CreateShipmentCommand {
    private final UUID orderId;
}
