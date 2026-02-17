package com.order_process.event.publish;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.order_process.config.RabbitConfig;
import com.order_process.event.OrderCreatedEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderEventPublisher {

	private final RabbitTemplate rabbitTemplate;
	
	public void publish(OrderCreatedEvent event) {
        rabbitTemplate.convertAndSend(
                RabbitConfig.ORDER_EXCHANGE,
                "order.created",
                event
        );
    }
}
