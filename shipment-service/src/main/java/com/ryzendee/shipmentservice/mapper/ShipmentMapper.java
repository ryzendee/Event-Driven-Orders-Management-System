package com.ryzendee.shipmentservice.mapper;

import com.ryzendee.kafka.models.commands.CreateShipmentCommand;
import com.ryzendee.kafka.models.events.shipment.ShipmentCreatedEvent;
import com.ryzendee.shipmentservice.dto.request.CreateShipmentRequest;
import com.ryzendee.shipmentservice.dto.response.ShipmentResponse;
import com.ryzendee.shipmentservice.entity.ShipmentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ShipmentMapper {
    CreateShipmentRequest map(CreateShipmentCommand command);
    ShipmentResponse map(ShipmentEntity entity);

    ShipmentCreatedEvent map(ShipmentResponse response);
}
