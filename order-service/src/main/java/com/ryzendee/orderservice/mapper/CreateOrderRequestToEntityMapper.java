package com.ryzendee.orderservice.mapper;

import com.ryzendee.orderservice.dto.request.CreateOrderRequest;
import com.ryzendee.orderservice.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CreateOrderRequestToEntityMapper {

    OrderEntity map(CreateOrderRequest request);
}
