package com.order_process.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.order_process.dto.request.CreateOrderRequest;
import com.order_process.event.OrderCreatedEvent;
import com.order_process.event.publish.OrderEventPublisher;

@Service
public class OrderService {
	
	@Autowired
	OrderEventPublisher orderEventPublisher;

	public ResponseEntity<?> PlaceOrder(CreateOrderRequest request) {
		
		String orderId = UUID.randomUUID().toString();

	    OrderCreatedEvent event = OrderCreatedEvent.builder()
	            .eventId(UUID.randomUUID().toString())
	            .orderId(orderId)
	            .userId(request.getUserId())
	            .amount(request.getAmount())
	            .createdAt(Instant.now())
	            .build();

	    orderEventPublisher.publish(event);
		
		return ResponseEntity.ok("Order Created");
	}
}
