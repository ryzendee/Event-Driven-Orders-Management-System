package com.ryzendee.orderservice.mapper;

import com.ryzendee.kafka.models.commands.order.ApproveOrderCommand;
import com.ryzendee.kafka.models.commands.order.RejectOrderCommand;
import com.ryzendee.kafka.models.commands.product.CancelProductReservationCommand;
import com.ryzendee.kafka.models.commands.product.ProductReservationCancelledEvent;
import com.ryzendee.kafka.models.commands.shipment.CreateShipmentCommand;
import com.ryzendee.kafka.models.commands.payment.ProcessPaymentCommand;
import com.ryzendee.kafka.models.commands.product.ReserveProductCommand;
import com.ryzendee.kafka.models.events.order.OrderCreatedEvent;
import com.ryzendee.kafka.models.events.payment.PaymentProcessFailedEvent;
import com.ryzendee.kafka.models.events.payment.PaymentProcessedEvent;
import com.ryzendee.kafka.models.events.product.ProductReservationFailedEvent;
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

    CancelProductReservationCommand map(PaymentProcessFailedEvent failedEvent);
    RejectOrderCommand map(ProductReservationFailedEvent failedEvent);
    RejectOrderCommand map(ProductReservationCancelledEvent cancelledEvent);
}
