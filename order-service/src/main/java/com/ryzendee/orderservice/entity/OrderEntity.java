package com.ryzendee.orderservice.entity;

import com.ryzendee.orderservice.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID customerId;
    private UUID productId;
    private Integer quantity;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
}
