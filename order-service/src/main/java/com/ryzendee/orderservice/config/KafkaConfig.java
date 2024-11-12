package com.ryzendee.orderservice.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
@RequiredArgsConstructor
public class KafkaConfig {

    private static final int TOPIC_PARTITIONS = 3;
    private static final int TOPIC_REPLICATION_FACTOR = 3;
    private final KafkaCommandTopicProperties topicsProperties;

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate(ProducerFactory<String, Object> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public NewTopic orderCommandsTopic() {
        return TopicBuilder.name(topicsProperties.getOrderCommandsTopic())
                .partitions(TOPIC_PARTITIONS)
                .replicas(TOPIC_REPLICATION_FACTOR)
                .build();
    }

    @Bean
    public NewTopic productCommandsTopic() {
        return TopicBuilder.name(topicsProperties.getProductCommandsTopic())
                .partitions(TOPIC_PARTITIONS)
                .replicas(TOPIC_REPLICATION_FACTOR)
                .build();
    }

    @Bean
    public NewTopic paymentCommandsTopic() {
        return TopicBuilder.name(topicsProperties.getPaymentCommandsTopic())
                .partitions(TOPIC_PARTITIONS)
                .replicas(TOPIC_REPLICATION_FACTOR)
                .build();
    }

    @Bean
    public NewTopic shipmentCommandsTopic() {
        return TopicBuilder.name(topicsProperties.getShipmentCommandsTopic())
                .partitions(TOPIC_PARTITIONS)
                .replicas(TOPIC_REPLICATION_FACTOR)
                .build();
    }
}
