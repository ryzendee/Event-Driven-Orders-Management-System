package com.ryzendee.productservice.handler;

import com.ryzendee.kafka.models.commands.product.CancelProductReservationCommand;
import com.ryzendee.kafka.models.commands.product.ProductReservationCancelledEvent;
import com.ryzendee.kafka.models.commands.product.ReserveProductCommand;
import com.ryzendee.kafka.models.events.product.ProductReservationFailedEvent;
import com.ryzendee.kafka.models.events.product.ProductReservedEvent;
import com.ryzendee.productservice.dto.response.ProductResponse;
import com.ryzendee.productservice.exception.ProductReservationException;
import com.ryzendee.productservice.mapper.EventMapper;
import com.ryzendee.productservice.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@KafkaListener(topics = "${topics.product.commands.name}")
@Slf4j
public class ProductCommandHandler {

    private final String productEventsTopic;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ProductService productService;
    private final EventMapper eventMapper;

    public ProductCommandHandler(@Value("${topics.product.events.name}") String productEventsTopic,
                                 KafkaTemplate<String, Object> kafkaTemplate,
                                 ProductService productService, EventMapper eventMapper) {
        this.productEventsTopic = productEventsTopic;
        this.kafkaTemplate = kafkaTemplate;
        this.productService = productService;
        this.eventMapper = eventMapper;
    }

    @KafkaHandler
    public void handleReserveProductCommand(@Payload ReserveProductCommand reserveProductCommand) {
        log.info("Received reserve product command: {}", reserveProductCommand);
        try {
            ProductResponse reservedProduct = productService.reserveProduct(reserveProductCommand.getProductId(), reserveProductCommand.getQuantity());
            ProductReservedEvent event = eventMapper.mapToReservedEvent(reserveProductCommand);
            kafkaTemplate.send(productEventsTopic, event);
        } catch (ProductReservationException ex) {
            log.error("Failed to reserve product", ex);
            ProductReservationFailedEvent event = eventMapper.mapToReservationFailedEvent(reserveProductCommand);
            kafkaTemplate.send(productEventsTopic, event);
        }
    }

    @KafkaHandler
    public void handleCancelProductReservationCommand(@Payload CancelProductReservationCommand command) {
        log.info("Received cancel product reservation command: {}", command);
        productService.cancelReservation(command.getProductId(), command.getQuantity());

        ProductReservationCancelledEvent event = eventMapper.mapToReservationCancelledEvent(command);
        kafkaTemplate.send(productEventsTopic, event);
    }

}
