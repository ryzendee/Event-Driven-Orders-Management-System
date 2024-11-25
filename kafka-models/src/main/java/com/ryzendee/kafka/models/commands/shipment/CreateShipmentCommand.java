package com.ryzendee.kafka.models.commands.shipment;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class CreateShipmentCommand {
    private final UUID orderId;
}
