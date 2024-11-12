package com.ryzendee.shipmentservice.service;

import com.ryzendee.shipmentservice.dto.request.CreateShipmentRequest;
import com.ryzendee.shipmentservice.dto.response.ShipmentResponse;
import com.ryzendee.shipmentservice.entity.ShipmentEntity;
import com.ryzendee.shipmentservice.mapper.ShipmentMapper;
import com.ryzendee.shipmentservice.repository.ShipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShipmentServiceImpl implements ShipmentService {

    private final ShipmentRepository shipmentRepository;
    private final ShipmentMapper shipmentMapper;
    @Override
    public ShipmentResponse createShipment(CreateShipmentRequest request) {
        ShipmentEntity entity = createShipmentFromRequest(request);
        shipmentRepository.save(entity);

        return shipmentMapper.map(entity);
    }

    private ShipmentEntity createShipmentFromRequest(CreateShipmentRequest request) {
        return ShipmentEntity.builder()
                .orderId(request.orderId())
                .build();
    }
}
