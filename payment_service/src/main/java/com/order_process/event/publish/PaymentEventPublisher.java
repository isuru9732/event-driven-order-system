package com.order_process.event.publish;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.order_process.config.RabbitConfig;
import com.order_process.event.PaymentCompletedEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentEventPublisher {

	private final RabbitTemplate rabbitTemplate;
	
	public void publish(PaymentCompletedEvent event) {
        rabbitTemplate.convertAndSend(
                RabbitConfig.PAYMENT_EXCHANGE,
                "payment.completed",
                event
        );
    } 
}
