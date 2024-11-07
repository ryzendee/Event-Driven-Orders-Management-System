package com.ryzendee.orderservice.service;

import com.ryzendee.kafka.models.events.OrderCreatedEvent;
import com.ryzendee.orderservice.dto.request.CreateOrderRequest;
import com.ryzendee.orderservice.dto.response.OrderResponse;
import com.ryzendee.orderservice.entity.OrderEntity;
import com.ryzendee.orderservice.enums.OrderStatus;
import com.ryzendee.orderservice.mapper.CreateOrderRequestToEntityMapper;
import com.ryzendee.orderservice.mapper.OrderEntityToCreatedEventMapper;
import com.ryzendee.orderservice.mapper.OrderEntityToResponseMapper;
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
    private final CreateOrderRequestToEntityMapper createOrderRequestToEntityMapper;
    private final OrderEntityToResponseMapper orderEntityToResponseMapper;
    private final OrderEntityToCreatedEventMapper orderEntityToCreatedEventMapper;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public OrderServiceImpl(@Value("${topics.order.events.name}") String orderEventsTopic,
                            OrderRepository orderRepository,
                            CreateOrderRequestToEntityMapper createOrderRequestToEntityMapper,
                            OrderEntityToResponseMapper orderEntityToResponseMapper,
                            OrderEntityToCreatedEventMapper orderEntityToCreatedEventMapper,
                            KafkaTemplate<String, Object> kafkaTemplate) {
        this.orderEventsTopic = orderEventsTopic;
        this.orderRepository = orderRepository;
        this.createOrderRequestToEntityMapper = createOrderRequestToEntityMapper;
        this.orderEntityToResponseMapper = orderEntityToResponseMapper;
        this.orderEntityToCreatedEventMapper = orderEntityToCreatedEventMapper;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Transactional
    @Override
    public OrderResponse createOrder(CreateOrderRequest request) {
        OrderEntity createdEntity = createEntity(request);
        orderRepository.save(createdEntity);
        mapToEventAndSendToKafka(createdEntity);

        return orderEntityToResponseMapper.map(createdEntity);
    }

    private OrderEntity createEntity(CreateOrderRequest request) {
        OrderEntity mappedEntity = createOrderRequestToEntityMapper.map(request);
        mappedEntity.setOrderStatus(OrderStatus.CREATED);

        return mappedEntity;
    }
    private void mapToEventAndSendToKafka(OrderEntity entity) {
        OrderCreatedEvent createdEvent = orderEntityToCreatedEventMapper.map(entity);
        kafkaTemplate.send(orderEventsTopic, createdEvent);
    }
}
