package com.ryzendee.productservice.service;

import com.ryzendee.productservice.dto.request.CreateProductRequest;
import com.ryzendee.productservice.dto.response.ProductResponse;

import java.util.UUID;

public interface ProductService {

    ProductResponse createProduct(CreateProductRequest request);
    ProductResponse reserveProduct(UUID productToReserveId, Integer quantityToReserve);
    void cancelReservation(UUID reservedProductId, Integer reservedQuantity);
}
