package com.order_process.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.order_process.entity.ProcessedEvent;
import com.order_process.event.OrderCreatedEvent;
import com.order_process.repository.ProcessedEventRepository;

import jakarta.transaction.Transactional;

@Service
public class PaymentService {

	@Autowired
	ProcessedEventRepository processedEventRepository;

	@Transactional
	public void processPayment(OrderCreatedEvent event) {

		if (processedEventRepository.existsById(UUID.fromString(event.getEventId()))) {
			return; // already processed
		}

		// simulate payment
		// charge customer
		
		processedEventRepository.save(new ProcessedEvent(UUID.fromString(event.getEventId()), Instant.now()));
	}
}
