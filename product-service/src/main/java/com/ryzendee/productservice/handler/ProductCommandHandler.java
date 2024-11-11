package com.ryzendee.productservice.handler;

import com.ryzendee.kafka.models.commands.ReserveProductCommand;
import com.ryzendee.kafka.models.events.product.ProductReservationFailedEvent;
import com.ryzendee.kafka.models.events.product.ProductReservedEvent;
import com.ryzendee.productservice.dto.response.ProductResponse;
import com.ryzendee.productservice.exception.ProductReservationException;
import com.ryzendee.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
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

    public ProductCommandHandler(@Value("${topics.product.events.name}") String productEventsTopic,
                                 KafkaTemplate<String, Object> kafkaTemplate,
                                 ProductService productService) {
        this.productEventsTopic = productEventsTopic;
        this.kafkaTemplate = kafkaTemplate;
        this.productService = productService;
    }

    @KafkaHandler
    public void handleReserveProductCommand(@Payload ReserveProductCommand reserveProductCommand) {
        log.info("Received reserve product command: {}", reserveProductCommand);
        try {
            ProductResponse reservedProduct = productService.reserveProduct(reserveProductCommand.getProductId(), reserveProductCommand.getQuantity());
            sendProductReservedEvent(reserveProductCommand.getOrderId(), reservedProduct);
        } catch (ProductReservationException ex) {
            log.error("Failed to reserve product", ex);
            sendProductReservationFailedEvent(reserveProductCommand);
        }
    }

    private void sendProductReservedEvent(UUID orderId, ProductResponse productResponse) {
        ProductReservedEvent productReservedEvent = new ProductReservedEvent(
                orderId,
                productResponse.id(),
                productResponse.price(),
                productResponse.quantity()
        );

        kafkaTemplate.send(productEventsTopic, productReservedEvent);
    }

    private void sendProductReservationFailedEvent(ReserveProductCommand reserveProductCommand) {
        ProductReservationFailedEvent reservationFailedEvent = new ProductReservationFailedEvent(
                reserveProductCommand.getOrderId(),
                reserveProductCommand.getProductId(),
                reserveProductCommand.getQuantity()
        );

        kafkaTemplate.send(productEventsTopic, reservationFailedEvent);
    }
}
