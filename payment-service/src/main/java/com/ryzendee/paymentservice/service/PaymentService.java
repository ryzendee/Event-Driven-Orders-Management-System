package com.ryzendee.paymentservice.service;

import com.ryzendee.paymentservice.dto.request.PaymentRequest;
import com.ryzendee.paymentservice.dto.response.PaymentResponse;
import jakarta.validation.Valid;

import java.math.BigDecimal;
import java.util.UUID;

public interface PaymentService {

    PaymentResponse processPayment(@Valid PaymentRequest request);
}
