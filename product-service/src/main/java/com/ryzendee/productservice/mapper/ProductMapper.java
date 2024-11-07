package com.ryzendee.productservice.mapper;

import com.ryzendee.productservice.dto.request.CreateProductRequest;
import com.ryzendee.productservice.dto.response.ProductResponse;
import com.ryzendee.productservice.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {

    ProductEntity map(CreateProductRequest request);
    ProductResponse map(ProductEntity entity);

}
