package com.ryzendee.orderservice.saga;

import com.ryzendee.kafka.models.commands.ReserveProductCommand;
import com.ryzendee.kafka.models.events.order.OrderCreatedEvent;
import com.ryzendee.orderservice.config.KafkaCommandTopicProperties;
import com.ryzendee.orderservice.mapper.CommandMapper;
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
public class OrderSaga {

    private final KafkaCommandTopicProperties topicProperties;
    private final CommandMapper commandMapper;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public OrderSaga(KafkaCommandTopicProperties kafkaCommandTopicProperties,
                     CommandMapper commandMapper,
                     KafkaTemplate<String, Object> kafkaTemplate) {
        this.topicProperties = kafkaCommandTopicProperties;
        this.commandMapper = commandMapper;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaHandler
    public void handleOrderCreatedEvent(@Payload OrderCreatedEvent orderCreatedEvent) {
        log.info("Received order created event: {}", orderCreatedEvent);
        ReserveProductCommand reserveProductCommand = commandMapper.map(orderCreatedEvent);

        kafkaTemplate.send(topicProperties.getOrderCommandsTopic(), reserveProductCommand);
    }
}
