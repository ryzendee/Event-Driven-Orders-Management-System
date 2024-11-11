package com.ryzendee.paymentservice.service;

import com.ryzendee.paymentservice.dto.request.PaymentRequest;
import com.ryzendee.paymentservice.dto.response.PaymentResponse;
import com.ryzendee.paymentservice.entity.PaymentEntity;
import com.ryzendee.paymentservice.mapper.PaymentMapper;
import com.ryzendee.paymentservice.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentMapper paymentMapper;
    private final PaymentRepository paymentRepository;

    @Override
    public PaymentResponse processPayment(PaymentRequest request) {
        BigDecimal totalPrice = calculateTotalOrderPrice(request);
        PaymentEntity entity = createPayment(totalPrice, request);
        paymentRepository.save(entity);

        return paymentMapper.map(entity);
    }

    private BigDecimal calculateTotalOrderPrice(PaymentRequest request) {
        return request.productPrice().multiply(
                new BigDecimal(request.productQuantity())
        );
    }

    private PaymentEntity createPayment(BigDecimal totalPrice, PaymentRequest request) {
        return PaymentEntity.builder()
                .orderId(request.orderId())
                .totalPrice(totalPrice)
                .build();
    }
}
