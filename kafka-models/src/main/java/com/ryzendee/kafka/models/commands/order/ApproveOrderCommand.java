package com.ryzendee.kafka.models.commands.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class ApproveOrderCommand {
    private final UUID orderId;
}
