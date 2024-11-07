package com.ryzendee.productservice.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateProductRequest(
        @NotBlank
        String name,

        @NotBlank
        String description,

        @NotNull
        @Min(0)
        Integer quantity,

        @NotNull
        @Min(1)
        BigDecimal price
) {
}
