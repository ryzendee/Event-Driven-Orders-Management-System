package com.ryzendee.kafka.models.events.shipment;

import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class ShipmentCreationFailedEvent {
    private final UUID orderId;
}
