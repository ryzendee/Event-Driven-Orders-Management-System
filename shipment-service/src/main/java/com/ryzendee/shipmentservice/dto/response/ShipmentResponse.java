package com.ryzendee.shipmentservice.dto.response;

import java.util.UUID;

public record ShipmentResponse(
        UUID id,
        UUID orderId
) {
}
