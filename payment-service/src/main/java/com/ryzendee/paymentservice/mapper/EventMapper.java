package com.ryzendee.paymentservice.mapper;

import com.ryzendee.kafka.models.commands.payment.ProcessPaymentCommand;
import com.ryzendee.kafka.models.events.payment.PaymentProcessFailedEvent;
import com.ryzendee.kafka.models.events.payment.PaymentProcessedEvent;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EventMapper {

    PaymentProcessedEvent mapToProcessedEvent(ProcessPaymentCommand command);
    PaymentProcessFailedEvent mapToProcessFailedEvent(ProcessPaymentCommand command);
}
