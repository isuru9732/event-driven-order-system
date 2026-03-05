package com.order_process.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.order_process.entity.ProcessedEvent;
import com.order_process.event.OrderCreatedEvent;
import com.order_process.event.PaymentCompletedEvent;
import com.order_process.event.publish.PaymentEventPublisher;
import com.order_process.repository.ProcessedEventRepository;

import jakarta.transaction.Transactional;

@Service
public class PaymentService {

	@Autowired
	ProcessedEventRepository processedEventRepository;

	@Autowired
	PaymentEventPublisher paymentEventPublisher;

	@Transactional
	public void processPayment(OrderCreatedEvent event) {
		System.out.print("Processing payment for event.getEventId(): " + event.getEventId());
		if (processedEventRepository.existsById(UUID.fromString(event.getEventId()))) {
			System.out.print("Event already processed, skipping");
			return; // already processed
		}

		// simulate payment
		// payment issue check and if payment.fail send to event order-service
		// charge customer

		processedEventRepository.save(new ProcessedEvent(UUID.fromString(event.getEventId()), Instant.now()));

		PaymentCompletedEvent paymentEvent = PaymentCompletedEvent.builder().eventId(UUID.randomUUID().toString())
				.orderId(event.getOrderId())
				.amount(event.getAmount()).userId(event.getUserId()).build();

		paymentEventPublisher.publish(paymentEvent);

	}
}
