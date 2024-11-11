package com.ryzendee.paymentservice.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentResponse(
        UUID id,
        UUID orderId,
        BigDecimal totalPrice

) {
}
