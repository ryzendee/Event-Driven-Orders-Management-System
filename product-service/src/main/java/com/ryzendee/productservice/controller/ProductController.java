package com.ryzendee.productservice.controller;

import com.ryzendee.productservice.dto.request.CreateProductRequest;
import com.ryzendee.productservice.dto.response.ProductResponse;
import com.ryzendee.productservice.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ProductResponse createProduct(@Valid CreateProductRequest request) {
        return productService.createProduct(request);
    }
}
