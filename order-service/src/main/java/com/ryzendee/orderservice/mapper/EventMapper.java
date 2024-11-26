package com.ryzendee.orderservice.mapper;

import com.ryzendee.kafka.models.commands.order.ApproveOrderCommand;
import com.ryzendee.kafka.models.commands.order.RejectOrderCommand;
import com.ryzendee.kafka.models.events.order.OrderApprovedEvent;
import com.ryzendee.kafka.models.events.order.OrderCreatedEvent;
import com.ryzendee.kafka.models.events.order.OrderRejectedEvent;
import com.ryzendee.orderservice.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EventMapper {

    OrderCreatedEvent mapToCreatedEvent(OrderEntity entity);
    OrderApprovedEvent mapToApprovedEvent(ApproveOrderCommand command);
    OrderRejectedEvent mapToRejectedEvent(RejectOrderCommand command);
}
