package com.ryzendee.orderservice.saga;

import com.ryzendee.kafka.models.commands.ReserveProductCommand;
import com.ryzendee.kafka.models.events.OrderCreatedEvent;
import com.ryzendee.orderservice.mapper.OrderCreatedEventToReserveProductCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;

@KafkaListener(topics = {
        "${topics.order.event.name}"
})
@Slf4j
public class OrderSaga {

    private final String productCommandsTopic;
    private final OrderCreatedEventToReserveProductCommand orderCreatedEventToReserveProductCommand;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public OrderSaga(@Value("${topics.product.commands.name}") String productCommandsTopic, OrderCreatedEventToReserveProductCommand orderCreatedEventToReserveProductCommand,
                     KafkaTemplate<String, Object> kafkaTemplate) {
        this.productCommandsTopic = productCommandsTopic;
        this.orderCreatedEventToReserveProductCommand = orderCreatedEventToReserveProductCommand;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaHandler
    public void handleOrderCreatedEvent(@Payload OrderCreatedEvent orderCreatedEvent) {
        log.info("Received order created event: {}", orderCreatedEvent);
        ReserveProductCommand reserveProductCommand = orderCreatedEventToReserveProductCommand.map(orderCreatedEvent);

        kafkaTemplate.send(productCommandsTopic, reserveProductCommand);
    }
}
