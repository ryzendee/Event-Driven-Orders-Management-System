package com.ryzendee.orderservice.mapper;

import com.ryzendee.kafka.models.commands.ReserveProductCommand;
import com.ryzendee.kafka.models.events.OrderCreatedEvent;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderCreatedEventToReserveProductCommand {

    ReserveProductCommand map(OrderCreatedEvent createdEvent);
}
