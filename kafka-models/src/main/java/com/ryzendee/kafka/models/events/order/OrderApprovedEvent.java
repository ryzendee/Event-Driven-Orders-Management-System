package com.ryzendee.kafka.models.events.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class OrderApprovedEvent {
    private final UUID orderId;
}
