package com.ryzendee.orderservice.service;

import com.ryzendee.orderservice.dto.request.CreateOrderRequest;
import com.ryzendee.orderservice.dto.response.OrderResponse;
import com.ryzendee.orderservice.entity.OrderEntity;
import com.ryzendee.orderservice.enums.OrderStatus;
import com.ryzendee.orderservice.mapper.CreateOrderRequestToEntityMapper;
import com.ryzendee.orderservice.mapper.OrderEntityToResponseMapper;
import com.ryzendee.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CreateOrderRequestToEntityMapper createOrderRequestToEntityMapper;
    private final OrderEntityToResponseMapper orderEntityToResponseMapper;

    @Transactional
    @Override
    public OrderResponse createOrder(CreateOrderRequest request) {
        OrderEntity mappedEntity = createOrderRequestToEntityMapper.map(request);
        mappedEntity.setOrderStatus(OrderStatus.CREATED);
        orderRepository.save(mappedEntity);

        return orderEntityToResponseMapper.map(mappedEntity);
    }
}
