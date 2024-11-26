# Event Driven Orders Management System
## [Read in Russian](README.ru.md)


## Table of Contents
- [Project Description](#project-description)
- [Architecture](#architecture)
- [Execution Scenarios](#execution-scenarios)
  - [Successful Scenario](#successful-scenario)
  - [Unsuccessful Scenario (Rollback)](#unsuccessful-scenario-rollback)

## Project Description
This project demonstrates the implementation of an orchestration saga using Kafka to manage the order processing flow. In this architecture, the **OrderService** acts as the orchestrator, managing the saga’s execution flow by interacting with other services through events and commands.



## Architecture
- **Order Service**: Responsible for managing orders and executing the main orchestration logic through the saga.
- **Product Service**: Handles the reservation of products for orders.
- **Payment Service**: Processes payments for orders.
- **Shipment Service**: Responsible for creating and shipping orders.



## Execution Scenarios

### Successful Scenario
![Blank diagram (1)](https://github.com/user-attachments/assets/6868134b-56a1-481a-af22-6d37e9ef4223)

#### Order Creation:
The customer creates a new order in **Order Service**, which triggers the `OrderCreatedEvent`.

#### Product Reservation:
The saga intercepts the `OrderCreatedEvent` and sends the `ReserveProductCommand` to **Product Service** to reserve the product for the order. If the reservation is successful, **Product Service** generates the `ProductReservedEvent`.

#### Payment Processing:
The saga receives the `ProductReservedEvent` and sends the `ProcessPaymentCommand` to **Payment Service** to process the payment for the order. If the payment is successfully processed, **Payment Service** publishes the `PaymentProcessedEvent`.

#### Shipment Creation:
Upon receiving the `PaymentProcessedEvent`, the saga sends the `CreateShipmentCommand` to **Shipment Service** to create the shipment for the order. Once the shipment is successfully created, **Shipment Service** generates the `ShipmentCreatedEvent`.

#### Order Confirmation:
The saga receives the `ShipmentCreatedEvent` and sends the `ApproveOrderCommand` to **Order Service** to approve the order. **Order Service** completes the process by generating the `OrderApprovedEvent`, which notifies that the order has been successfully processed.


### Unsuccessful Scenario (Rollback)

#### Payment Processing Error:
If an error occurs during payment processing (e.g., payment rejection), **Payment Service** publishes the `PaymentProcessedFailedEvent`.

#### Cancel Product Reservation:
The saga intercepts the `PaymentProcessedFailedEvent` and sends the `CancelProductReservationCommand` to **Product Service** to cancel the previously reserved products. If the cancellation is successful, **Product Service** generates the `ProductReservationCancelledEvent`.

#### Order Rejection:
The saga intercepts the `ProductReservationCancelledEvent` and sends the `RejectOrderCommand` to **Order Service** to reject the order. Once **Order Service** receives the `RejectOrderCommand`, it rejects the order and completes the rollback process by generating the `OrderRejectedEvent`, which notifies that the order has been rejected.
