# Event Driven Orders Management System
## [Read in English](README.md)



## Оглавление
- [Описание проекта](#описание-проекта)
- [Архитектура](#архитектура)
- [Сценарии выполнения](#сценарии-выполнения)
    - [Успешный сценарий](#успешный-сценарий)
    - [Неуспешный сценарий (откат)](#неуспешный-сценарий-откат)

## Описание проекта
Этот проект демонстрирует реализацию оркестровой саги при помощи Kafka для управления процессом обработки заказа.
В данной архитектуре OrderService выполняет роль оркестратора и управляет ходом выполнения саги, взаимодействуя с другими сервисами с помощью событий и команд.



## Архитектура
- Order Service: отвечает за управление заказами и выполнение основной логики оркестрации через сагу.
- Product Service: обрабатывает резервирование товаров для заказов.
- Payment Service: обрабатывает платежи для заказов.
- Shipment Service: отвечает за создание и отправку заказов.


## Сценарии выполнения

### Успешный сценарий
![Blank diagram (1)](https://github.com/user-attachments/assets/6868134b-56a1-481a-af22-6d37e9ef4223)

#### Создание заказа:
Клиент создает новый заказ в Order Service. Это действие инициирует событие `OrderCreatedEvent`.

#### Резервирование товара:
Сага перехватывает `OrderCreatedEvent` и отправляет команду `ReserveProductCommand` в Product Service, который резервирует товар для заказа.
Если резервирование успешно, Product Service генерирует событие `ProductReservedEvent`.

#### Обработка платежа:
Сага получает `ProductReservedEvent` и отправляет команду `ProcessPaymentCommand` в Payment Service для обработки платежа по заказу.
Если платеж успешно обработан, Payment Service отправляет событие `PaymentProcessedEvent`.

#### Создание отправления:
Получив `PaymentProcessedEvent`, сага отправляет команду `CreateShipmentCommand` в Shipment Service для создания отправления заказа.
Когда отправление успешно создано, Shipment Service отправляет событие `ShipmentCreatedEvent`.

#### Подтверждение заказа:
Сага получает `ShipmentCreatedEvent` и отправляет команду `ApproveOrderCommand` в Order Service для окончательного подтверждения заказа.
Order Service завершает процесс, генерируя событие `OrderApprovedEvent`, уведомляющее о том, что заказ успешно обработан.


### Неуспешный сценарий (откат)

#### Ошибка обработки платежа:
Если на этапе обработки платежа возникает ошибка (например, отклонение платежа), Payment Service публикует событие `PaymentProcessedFailedEvent`.

#### Отмена резервирования товара:
Сага перехватывает событие `PaymentProcessedFailedEvent` и отправляет команду `CancelProductReservationCommand` в Product Service для отмены ранее зарезервированных товаров.
Если отмена проходит успешно, Product Service генерирует событие `ProductReservationCancelledEvent`.

#### Отклонение заказа:
Сага перехватывает событие `ProductReservationCancelledEvent` и отправляет команду `RejectOrderCommand` в Order Service для отклонения заказа.
Когда Order Service получает команду `RejectOrderCommand`, он отклоняет заказ и завершает процесс отката, генерируя событие `OrderRejectedEvent`, уведомляющее о том, что заказ был отклонен.

