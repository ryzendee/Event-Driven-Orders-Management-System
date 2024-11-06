package com.ryzendee.orderservice.mapper;

import com.ryzendee.orderservice.dto.response.OrderResponse;
import com.ryzendee.orderservice.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderEntityToResponseMapper {

    OrderResponse map(OrderEntity entity);
}