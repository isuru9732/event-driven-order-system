package com.order_process.event.publish;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.order_process.config.RabbitConfig;
import com.order_process.event.InventoryUpdatedEvent;
import com.order_process.event.PaymentCompletedEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryEventPublisher {

	private final RabbitTemplate rabbitTemplate;
	
	public void publish(InventoryUpdatedEvent event) {
        rabbitTemplate.convertAndSend(
                RabbitConfig.INVENTORY_EXCHANGE,
                RabbitConfig.INVENTORY_UPDATED,
                event
        );
    } 
}
