package com.ryzendee.shipmentservice.handler;

import com.ryzendee.kafka.models.commands.shipment.CreateShipmentCommand;
import com.ryzendee.kafka.models.events.shipment.ShipmentCreatedEvent;
import com.ryzendee.shipmentservice.dto.request.CreateShipmentRequest;
import com.ryzendee.shipmentservice.dto.response.ShipmentResponse;
import com.ryzendee.shipmentservice.mapper.ShipmentMapper;
import com.ryzendee.shipmentservice.service.ShipmentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = "${topics.shipment.commands.name}")
public class ShipmentCommandHandler {

    private final String shipmentEventsTopic;
    private final ShipmentMapper shipmentMapper;
    private final ShipmentService shipmentService;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public ShipmentCommandHandler(@Value("${topics.shipment.events.name}") String shipmentEventsTopic,
                                  ShipmentMapper shipmentMapper,
                                  ShipmentService shipmentService,
                                  KafkaTemplate<String, Object> kafkaTemplate) {
        this.shipmentEventsTopic = shipmentEventsTopic;
        this.shipmentMapper = shipmentMapper;
        this.shipmentService = shipmentService;
        this.kafkaTemplate = kafkaTemplate;
    }


    @KafkaHandler
    public void handleCreateShipmentCommand(@Payload CreateShipmentCommand command) {
        ShipmentResponse response = createShipment(command);
        sendShipmentCreatedEvent(response);
    }

    private ShipmentResponse createShipment(CreateShipmentCommand command) {
        CreateShipmentRequest request = shipmentMapper.map(command);
        return shipmentService.createShipment(request);
    }
    private void sendShipmentCreatedEvent(ShipmentResponse response) {
        ShipmentCreatedEvent event = shipmentMapper.map(response);
        kafkaTemplate.send(shipmentEventsTopic, event);
    }
}
