package com.ryzendee.paymentservice.mapper;

import com.ryzendee.kafka.models.commands.ProcessPaymentCommand;
import com.ryzendee.paymentservice.dto.request.PaymentRequest;
import com.ryzendee.paymentservice.dto.response.PaymentResponse;
import com.ryzendee.paymentservice.entity.PaymentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PaymentMapper {

    PaymentRequest map(ProcessPaymentCommand command);
    PaymentResponse map(PaymentEntity entity);
}
