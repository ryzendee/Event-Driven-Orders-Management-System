package com.ryzendee.orderservice.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class KafkaCommandTopicProperties {
    @Value("${topics.order.commands.name}")
    private String orderCommandsTopic;

    @Value("${topics.product.commands.name}")
    private String productCommandsTopic;

    @Value("${topics.payment.commands.name}")
    private String paymentCommandsTopic;

    @Value("${topics.shipment.commands.name}")
    private String shipmentCommandsTopic;

}
