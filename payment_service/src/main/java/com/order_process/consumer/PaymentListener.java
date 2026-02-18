package com.order_process.consumer;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.order_process.event.OrderCreatedEvent;
import com.order_process.service.PaymentService;
import com.rabbitmq.client.Channel;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PaymentListener {

	private final PaymentService paymentService;

	@RabbitListener(queues = "payment.queue")
	public void handleOrderCreated(OrderCreatedEvent event, Channel channel, Message message) throws IOException {

		try {
			paymentService.processPayment(event);

			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

		} catch (Exception ex) {
			channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
		}
	}
}
