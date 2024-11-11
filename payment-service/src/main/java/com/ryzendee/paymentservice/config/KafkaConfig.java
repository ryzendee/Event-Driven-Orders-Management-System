package com.ryzendee.paymentservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    private static final int TOPIC_PARTITIONS = 3;
    private static final int TOPIC_REPLICATION_FACTOR = 3;


    @Bean
    public NewTopic createPaymentEventsTopic(@Value("${topics.payment.events.name}") String paymentEventsTopic) {
        return TopicBuilder.name(paymentEventsTopic)
                .partitions(TOPIC_PARTITIONS)
                .replicas(TOPIC_REPLICATION_FACTOR)
                .build();
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate(ProducerFactory<String, Object> producerFactory) {
        return new KafkaTemplate(producerFactory);
    }
}
