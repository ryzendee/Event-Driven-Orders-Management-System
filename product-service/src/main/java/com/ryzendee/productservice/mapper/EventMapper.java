package com.ryzendee.productservice.mapper;

import com.ryzendee.kafka.models.commands.product.CancelProductReservationCommand;
import com.ryzendee.kafka.models.commands.product.ProductReservationCancelledEvent;
import com.ryzendee.kafka.models.commands.product.ReserveProductCommand;
import com.ryzendee.kafka.models.events.product.ProductReservationFailedEvent;
import com.ryzendee.kafka.models.events.product.ProductReservedEvent;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EventMapper {


    ProductReservedEvent mapToReservedEvent(ReserveProductCommand command);
    ProductReservationFailedEvent mapToReservationFailedEvent(ReserveProductCommand command);
    ProductReservationCancelledEvent mapToReservationCancelledEvent(CancelProductReservationCommand command);
}
