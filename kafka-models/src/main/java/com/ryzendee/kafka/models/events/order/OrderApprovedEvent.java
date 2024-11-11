package com.ryzendee.kafka.models.events.order;

import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class OrderApprovedEvent {
    private final UUID orderId;
}
