package com.ryzendee.paymentservice.repository;

import com.ryzendee.paymentservice.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentRepository extends JpaRepository<PaymentEntity, UUID> {
}