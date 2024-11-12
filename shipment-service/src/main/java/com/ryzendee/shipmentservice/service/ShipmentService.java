package com.ryzendee.shipmentservice.service;

import com.ryzendee.shipmentservice.dto.request.CreateShipmentRequest;
import com.ryzendee.shipmentservice.dto.response.ShipmentResponse;

public interface ShipmentService {

    ShipmentResponse createShipment(CreateShipmentRequest request);
}
