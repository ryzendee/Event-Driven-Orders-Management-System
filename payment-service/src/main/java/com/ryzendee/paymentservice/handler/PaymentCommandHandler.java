package com.ryzendee.paymentservice.handler;

import com.ryzendee.kafka.models.commands.payment.ProcessPaymentCommand;
import com.ryzendee.kafka.models.events.payment.PaymentProcessFailedEvent;
import com.ryzendee.kafka.models.events.payment.PaymentProcessedEvent;
import com.ryzendee.paymentservice.dto.request.PaymentRequest;
import com.ryzendee.paymentservice.dto.response.PaymentResponse;
import com.ryzendee.paymentservice.mapper.PaymentMapper;
import com.ryzendee.paymentservice.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = "${topics.payment.command.name}")
@Slf4j
public class PaymentCommandHandler {

    private final String paymentEventsTopic;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final PaymentMapper paymentMapper;
    private final PaymentService paymentService;

    public PaymentCommandHandler(@Value("${topics.payment.events.name}") String paymentEventsTopic,
                                 KafkaTemplate<String, Object> kafkaTemplate,
                                 PaymentMapper paymentMapper,
                                 PaymentService paymentService) {
        this.paymentEventsTopic = paymentEventsTopic;
        this.kafkaTemplate = kafkaTemplate;
        this.paymentMapper = paymentMapper;
        this.paymentService = paymentService;
    }

    @KafkaHandler
    public void handlePaymentProcessCommand(@Payload ProcessPaymentCommand command) {
        log.info("Received payment process command: {}", command);

        PaymentRequest request = paymentMapper.map(command);

        try {
            PaymentResponse response = paymentService.processPayment(request);
            sendPaymentProcessedEvent(response);
        } catch (Exception ex) {
            log.error("Failed to process payment", ex);
            sendPaymentProcessFailedEvent(command);
        }
    }

    private void sendPaymentProcessedEvent(PaymentResponse paymentResponse) {
        PaymentProcessedEvent event = new PaymentProcessedEvent(
                paymentResponse.id(),
                paymentResponse.orderId()
        );

        kafkaTemplate.send(paymentEventsTopic, event);
    }

    private void sendPaymentProcessFailedEvent(ProcessPaymentCommand command) {
        PaymentProcessFailedEvent event = new PaymentProcessFailedEvent(
                command.getOrderId(),
                command.getProductId(),
                command.getProductPrice(),
                command.getProductQuantity()
        );

        kafkaTemplate.send(paymentEventsTopic, event);
    }
}
