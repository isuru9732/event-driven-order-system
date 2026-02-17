# ORDER PROCESSING SYSTEM

-- System Requirements
> Functional

Place order
Process payment
Update inventory
Send notification

> Non-Functional

High availability
Fault tolerance
Scalability
Observability
Idempotency

Architecture Overview
Microservices:

API Gateway
Order Service
Payment Service
Inventory Service
Notification Service
Message Broker

Flow
Client → API Gateway → Order Service → Queue → Payment → Inventory → Notification

design before code

architecture style => even driven

High-Level Architecture

Client
   ↓
API Gateway
   ↓
Order Service  →  RabbitMQ  →  Payment Service
                                 ↓
                              Inventory Service
                                 ↓
                              Notification Service



🏛 Architecture Patterns We’re Using

This is important. Remember this.

1️⃣ Microservices Architecture

Services are independently deployable.

2️⃣ API Gateway Pattern

Single entry point.

3️⃣ Event-Driven Architecture

Services communicate via events.

4️⃣ Saga Pattern (Choreography)

Each service reacts to events.

No central coordinator.


Structure
│
├── api-gateway/
├── order-service/
├── payment-service/
├── inventory-service/
├── notification-service/
├── docker-compose.yml
└── README.md
