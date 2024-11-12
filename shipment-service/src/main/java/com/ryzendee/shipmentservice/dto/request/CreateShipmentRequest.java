package com.ryzendee.shipmentservice.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateShipmentRequest(
        @NotNull
        UUID orderId
) {
}
