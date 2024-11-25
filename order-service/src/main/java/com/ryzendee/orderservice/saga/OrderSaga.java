package com.ryzendee.orderservice.saga;

import com.ryzendee.kafka.models.commands.order.ApproveOrderCommand;
import com.ryzendee.kafka.models.commands.order.RejectOrderCommand;
import com.ryzendee.kafka.models.commands.product.CancelProductReservationCommand;
import com.ryzendee.kafka.models.commands.product.ProductReservationCancelledEvent;
import com.ryzendee.kafka.models.commands.shipment.CreateShipmentCommand;
import com.ryzendee.kafka.models.commands.payment.ProcessPaymentCommand;
import com.ryzendee.kafka.models.commands.product.ReserveProductCommand;
import com.ryzendee.kafka.models.events.order.OrderApprovedEvent;
import com.ryzendee.kafka.models.events.order.OrderCreatedEvent;
import com.ryzendee.kafka.models.events.payment.PaymentProcessFailedEvent;
import com.ryzendee.kafka.models.events.payment.PaymentProcessedEvent;
import com.ryzendee.kafka.models.events.product.ProductReservationFailedEvent;
import com.ryzendee.kafka.models.events.product.ProductReservedEvent;
import com.ryzendee.kafka.models.events.shipment.ShipmentCreatedEvent;
import com.ryzendee.orderservice.config.KafkaCommandTopicProperties;
import com.ryzendee.orderservice.enums.OrderStatus;
import com.ryzendee.orderservice.mapper.CommandMapper;
import com.ryzendee.orderservice.service.OrderHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;

@KafkaListener(topics = {
        "${topics.order.events.name}",
        "${topics.product.events.name}",
        "${topics.payment.events.name}",
        "${topics.shipment.events.name}"
})
@Slf4j
@RequiredArgsConstructor
public class OrderSaga {

    private final KafkaCommandTopicProperties topicProperties;
    private final CommandMapper commandMapper;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final OrderHistoryService orderHistoryService;

    @KafkaHandler
    public void handleOrderCreatedEvent(@Payload OrderCreatedEvent event) {
        log.info("Received order created event: {}", event);
        orderHistoryService.addOrder(event.getOrderId(), OrderStatus.CREATED);

        ReserveProductCommand command = commandMapper.map(event);
        kafkaTemplate.send(topicProperties.getOrderCommandsTopic(), command);
    }

    @KafkaHandler
    public void handleProductReservedEvent(@Payload ProductReservedEvent event) {
        log.info("Received product reserved event: {}", event);
        ProcessPaymentCommand command = commandMapper.map(event);
        kafkaTemplate.send(topicProperties.getPaymentCommandsTopic(), command);
    }

    @KafkaHandler
    public void handleProductReservationFailedEvent(@Payload ProductReservationFailedEvent event) {
        log.info("Received product reservation failed event: {}", event);
        RejectOrderCommand command = commandMapper.map(event);
        kafkaTemplate.send(topicProperties.getOrderCommandsTopic(), command);
    }

    @KafkaHandler
    public void handlePaymentProcessedEvent(@Payload PaymentProcessedEvent event) {
        log.info("Received payment processed event: {}", event);
        CreateShipmentCommand command = commandMapper.map(event);
        kafkaTemplate.send(topicProperties.getShipmentCommandsTopic(), command);
    }

    @KafkaHandler
    public void handlePaymentProcessFailedEvent(@Payload PaymentProcessFailedEvent event) {
        log.info("Received payment process failed event: {}", event);
        CancelProductReservationCommand command = commandMapper.map(event);
        kafkaTemplate.send(topicProperties.getProductCommandsTopic(), command);
    }

    @KafkaHandler
    public void handleProductReservationCancelledEvent(@Payload ProductReservationCancelledEvent event) {
        log.info("Received product reservation cancelled event: {}", event);
        RejectOrderCommand command = commandMapper.map(event);
        kafkaTemplate.send(topicProperties.getOrderCommandsTopic(), command);
    }

    @KafkaHandler
    public void handleShipmentCreatedEvent(@Payload ShipmentCreatedEvent event) {
        log.info("Received shipment created event: {}", event);
        ApproveOrderCommand command = commandMapper.map(event);
        kafkaTemplate.send(topicProperties.getOrderCommandsTopic(), command);
    }

    @KafkaHandler
    public void handleOrderApprovedEvent(@Payload OrderApprovedEvent event) {
        log.info("Received order approved event: {}", event);
    }
}
