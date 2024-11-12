package com.ryzendee.shipmentservice.repository;

import com.ryzendee.shipmentservice.entity.ShipmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ShipmentRepository extends JpaRepository<ShipmentEntity, UUID> {
}