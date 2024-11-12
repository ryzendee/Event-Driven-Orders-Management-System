package com.ryzendee.orderservice.mapper;

import com.ryzendee.kafka.models.commands.ApproveOrderCommand;
import com.ryzendee.kafka.models.commands.CreateShipmentCommand;
import com.ryzendee.kafka.models.commands.ProcessPaymentCommand;
import com.ryzendee.kafka.models.commands.ReserveProductCommand;
import com.ryzendee.kafka.models.events.order.OrderCreatedEvent;
import com.ryzendee.kafka.models.events.payment.PaymentProcessedEvent;
import com.ryzendee.kafka.models.events.product.ProductReservedEvent;
import com.ryzendee.kafka.models.events.shipment.ShipmentCreatedEvent;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommandMapper {

    ReserveProductCommand map(OrderCreatedEvent createdEvent);
    ProcessPaymentCommand map(ProductReservedEvent reservedEvent);
    CreateShipmentCommand map(PaymentProcessedEvent processedEvent);
    ApproveOrderCommand map(ShipmentCreatedEvent createdEvent);
}
