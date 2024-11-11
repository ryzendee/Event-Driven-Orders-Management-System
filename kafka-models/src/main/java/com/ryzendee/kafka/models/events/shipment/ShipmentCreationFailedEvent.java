package com.ryzendee.kafka.models.events.shipment;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class ShipmentCreationFailedEvent {
    private final UUID orderId;
}
