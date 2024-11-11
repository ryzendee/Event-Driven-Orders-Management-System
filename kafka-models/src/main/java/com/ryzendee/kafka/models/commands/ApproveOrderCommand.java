package com.ryzendee.kafka.models.commands;

import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class ApproveOrderCommand {
    private final UUID orderId;
}
