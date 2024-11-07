package com.ryzendee.productservice.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductResponse(
        UUID id,
        String name,
        String description,
        Integer quantity,
        BigDecimal price
) {
}
