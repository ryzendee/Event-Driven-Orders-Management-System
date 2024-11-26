package com.ryzendee.orderservice.handler;

import com.ryzendee.kafka.models.commands.order.ApproveOrderCommand;
import com.ryzendee.kafka.models.commands.order.RejectOrderCommand;
import com.ryzendee.kafka.models.events.order.OrderApprovedEvent;
import com.ryzendee.kafka.models.events.order.OrderRejectedEvent;
import com.ryzendee.orderservice.mapper.EventMapper;
import com.ryzendee.orderservice.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = "${topics.order.command.name}")
@Slf4j
public class OrderCommandHandler {

    private final String orderEventsTopic;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final EventMapper eventMapper;
    private final OrderService orderService;

    public OrderCommandHandler(@Value("${topics.order.events.name}") String orderEventsTopic,
                               KafkaTemplate<String, Object> kafkaTemplate,
                               EventMapper eventMapper,
                               OrderService orderService) {
        this.orderEventsTopic = orderEventsTopic;
        this.kafkaTemplate = kafkaTemplate;
        this.eventMapper = eventMapper;
        this.orderService = orderService;
    }

    @KafkaHandler
    public void handleApproveOrderCommand(@Payload ApproveOrderCommand command) {
        log.info("Received approve order command: {}", command);
        orderService.approveOrder(command.getOrderId());
        OrderApprovedEvent event = eventMapper.mapToApprovedEvent(command);
        kafkaTemplate.send(orderEventsTopic, event);
    }

    @KafkaHandler
    public void handleRejectOrderCommand(@Payload RejectOrderCommand command) {
        log.info("Received reject order command: {}", command);
        orderService.rejectOrder(command.getOrderId());
        OrderRejectedEvent event = eventMapper.mapToRejectedEvent(command);
        kafkaTemplate.send(orderEventsTopic, event);
    }
}
