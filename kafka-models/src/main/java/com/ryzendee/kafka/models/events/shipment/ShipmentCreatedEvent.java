package com.ryzendee.kafka.models.events.shipment;

import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class ShipmentCreatedEvent {
    private final UUID orderId;
    private final UUID shipmentId;
}
