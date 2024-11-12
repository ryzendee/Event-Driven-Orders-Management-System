package com.ryzendee.orderservice.service;

import com.ryzendee.kafka.models.events.order.OrderCreatedEvent;
import com.ryzendee.orderservice.dto.request.CreateOrderRequest;
import com.ryzendee.orderservice.dto.response.OrderResponse;
import com.ryzendee.orderservice.entity.OrderEntity;
import com.ryzendee.orderservice.enums.OrderStatus;
import com.ryzendee.orderservice.mapper.CreateOrderRequestToEntityMapper;
import com.ryzendee.orderservice.mapper.EventMapper;
import com.ryzendee.orderservice.mapper.OrderEntityToCreatedEventMapper;
import com.ryzendee.orderservice.mapper.OrderEntityToResponseMapper;
import com.ryzendee.orderservice.mapper.OrderMapper;
import com.ryzendee.orderservice.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final String orderEventsTopic;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final EventMapper eventMapper;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public OrderServiceImpl(@Value("${topics.order.events.name}") String orderEventsTopic,
                            OrderRepository orderRepository,
                            OrderMapper orderMapper,
                            EventMapper eventMapper,
                            KafkaTemplate<String, Object> kafkaTemplate) {
        this.orderEventsTopic = orderEventsTopic;
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.eventMapper = eventMapper;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Transactional
    @Override
    public OrderResponse createOrder(CreateOrderRequest request) {
        OrderEntity createdEntity = createEntity(request);
        orderRepository.save(createdEntity);
        mapToEventAndSendToKafka(createdEntity);

        return orderMapper.map(createdEntity);
    }

    private OrderEntity createEntity(CreateOrderRequest request) {
        OrderEntity mappedEntity = orderMapper.map(request);
        mappedEntity.setOrderStatus(OrderStatus.CREATED);

        return mappedEntity;
    }
    private void mapToEventAndSendToKafka(OrderEntity entity) {
        OrderCreatedEvent createdEvent = eventMapper.map(entity);
        kafkaTemplate.send(orderEventsTopic, createdEvent);
    }
}
