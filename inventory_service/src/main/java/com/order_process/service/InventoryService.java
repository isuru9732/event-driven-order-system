package com.order_process.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.order_process.entity.ProcessedEvent;
import com.order_process.event.InventoryUpdatedEvent;
import com.order_process.event.PaymentCompletedEvent;
import com.order_process.event.publish.InventoryEventPublisher;
import com.order_process.repository.ProcessedEventRepository;

import jakarta.transaction.Transactional;

@Service
public class InventoryService {

	@Autowired
	ProcessedEventRepository processedEventRepository;

	@Autowired
	InventoryEventPublisher inventoryEventPublisher;

	@Transactional
	public void reserveOrDeductStock(PaymentCompletedEvent event) {

		try {
			if (processedEventRepository.existsById(UUID.fromString(event.getEventId()))) {
				System.out.print("Payment Event already processed, skipping");
				return; // already processed
			}

			// TODO: Deduct stock / reserve stock here
			// For now, just log:
			System.out.println("Stock updated for orderId=" + event.getOrderId());

			processedEventRepository.save(new ProcessedEvent(UUID.fromString(event.getEventId()), Instant.now()));

			InventoryUpdatedEvent inventoryEvent = InventoryUpdatedEvent.builder().eventId(UUID.randomUUID().toString())
					.build();

			inventoryEventPublisher.publish(inventoryEvent);
		} catch (Exception e) {
			throw e;
		}

	}
}
