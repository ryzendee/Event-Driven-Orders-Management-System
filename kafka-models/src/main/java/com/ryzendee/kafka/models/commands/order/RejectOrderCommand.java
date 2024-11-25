package com.ryzendee.kafka.models.commands.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class RejectOrderCommand {
    private final UUID orderId;
}
