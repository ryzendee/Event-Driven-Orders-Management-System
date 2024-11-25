package com.ryzendee.orderservice.service;

import com.ryzendee.orderservice.entity.OrderEntity;
import com.ryzendee.orderservice.entity.OrderHistoryEntity;
import com.ryzendee.orderservice.enums.OrderStatus;
import com.ryzendee.orderservice.repository.OrderHistoryRepository;
import com.ryzendee.orderservice.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderHistoryServiceImpl implements OrderHistoryService {

    private final OrderRepository orderRepository;
    private final OrderHistoryRepository orderHistoryRepository;

    @Transactional
    @Override
    public void addOrder(UUID orderId, OrderStatus status) {
        OrderEntity order = getOrderById(orderId);

        OrderHistoryEntity orderHistoryEntity = OrderHistoryEntity.builder()
                .order(order)
                .orderStatus(status)
                .build();

        orderHistoryRepository.save(orderHistoryEntity);
    }

    private OrderEntity getOrderById(UUID id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found! Id : " + id));
    }
}
