package com.order_process.event;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreatedEvent {

	private String eventId;
    private String orderId;
    private String userId;
    private Double amount;
    private Instant createdAt;
}
