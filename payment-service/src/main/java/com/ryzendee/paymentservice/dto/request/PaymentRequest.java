package com.ryzendee.paymentservice.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentRequest(
        @NotNull
        UUID orderId,
        @NotNull
        @Min(1)
        Integer productQuantity,
        @NotNull
        @Min(0)
        BigDecimal productPrice
) {
}
