package com.ryzendee.orderservice.service;

import com.ryzendee.kafka.models.events.order.OrderApprovedEvent;
import com.ryzendee.kafka.models.events.order.OrderCreatedEvent;
import com.ryzendee.orderservice.dto.request.CreateOrderRequest;
import com.ryzendee.orderservice.dto.response.OrderResponse;
import com.ryzendee.orderservice.entity.OrderEntity;
import com.ryzendee.orderservice.enums.OrderStatus;
import com.ryzendee.orderservice.mapper.EventMapper;
import com.ryzendee.orderservice.mapper.OrderMapper;
import com.ryzendee.orderservice.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

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
        OrderEntity createdEntity = orderMapper.map(request);
        orderRepository.save(createdEntity);

        OrderCreatedEvent createdEvent = eventMapper.mapToCreatedEvent(createdEntity);
        kafkaTemplate.send(orderEventsTopic, createdEvent);

        return orderMapper.map(createdEntity);
    }

    @Transactional
    @Override
    public void approveOrder(UUID orderId) {
        OrderEntity order = getOrderById(orderId);
        order.setOrderStatus(OrderStatus.APPROVED);
        orderRepository.save(order);

        OrderApprovedEvent approvedEvent = eventMapper.mapToApprovedEvent(order);
        kafkaTemplate.send(orderEventsTopic, approvedEvent);
    }

    private OrderEntity getOrderById(UUID id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found! Id : " + id));
    }
}
