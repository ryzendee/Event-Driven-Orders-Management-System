package com.ryzendee.productservice.service;

import com.ryzendee.productservice.dto.request.CreateProductRequest;
import com.ryzendee.productservice.dto.response.ProductResponse;
import com.ryzendee.productservice.entity.ProductEntity;
import com.ryzendee.productservice.exception.ProductNotFoundException;
import com.ryzendee.productservice.exception.ProductReservationException;
import com.ryzendee.productservice.repository.ProductRepository;
import com.ryzendee.productservice.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Qualifier("productEventsTopic")
    private final NewTopic productEventsTopic;

    @Override
    public ProductResponse createProduct(CreateProductRequest request) {
        ProductEntity mappedEntity = productMapper.map(request);
        productRepository.save(mappedEntity);

        return mapToProductResponse(mappedEntity);
    }

    @Override
    public ProductResponse reserveProduct(UUID productToReserveId, Integer quantityToReserve) {
        ProductEntity productToReserve = getById(productToReserveId);

        if (!hasEnoughtQuantityToReserve(productToReserve, quantityToReserve)) {
            throw new ProductReservationException("Failed to reserve product, quantity is insufficient. Actual quantity: " + productToReserve.getQuantity());
        }

        Integer remainsQuantity = productToReserve.getQuantity() - quantityToReserve;
        productToReserve.setQuantity(remainsQuantity);
        productRepository.save(productToReserve);

        return mapToProductResponse(productToReserve);
    }

    @Override
    public void cancelReservation(UUID reservedProductId, Integer reservedQuantity) {
        ProductEntity reservedProduct = getById(reservedProductId);

        Integer restoredQuantity = reservedProduct.getQuantity() + reservedQuantity;
        reservedProduct.setQuantity(restoredQuantity);
        productRepository.save(reservedProduct);
    }

    private ProductEntity getById(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with given id not found: " + id));
    }

    private ProductResponse mapToProductResponse(ProductEntity entity) {
        return productMapper.map(entity);
    }
    private boolean hasEnoughtQuantityToReserve(ProductEntity productToReserve, Integer quantityToReserve) {
        return productToReserve.getQuantity() >= quantityToReserve;
    }
}
