package com.ryzendee.orderservice.mapper;

import com.ryzendee.kafka.models.events.order.OrderCreatedEvent;
import com.ryzendee.orderservice.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EventMapper {

    OrderCreatedEvent map(OrderEntity entity);
}
