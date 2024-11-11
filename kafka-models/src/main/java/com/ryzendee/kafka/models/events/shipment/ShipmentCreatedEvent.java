package com.ryzendee.kafka.models.events.shipment;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class ShipmentCreatedEvent {
    private final UUID orderId;
    private final UUID shipmentId;
}
