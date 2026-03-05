package com.order_process.event.consumer;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.order_process.config.RabbitConfig;
import com.order_process.event.PaymentCompletedEvent;
import com.order_process.service.InventoryService;
import com.rabbitmq.client.Channel;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InventoryListener {

	private final InventoryService inventoryService;

	@RabbitListener(queues = RabbitConfig.INVENTORY_QUEUE)
	public void handlePaymentCompleted(PaymentCompletedEvent event, Channel channel, Message message) throws IOException {

		try {
			System.out.println("payment completed Event recieved");
			inventoryService.reserveOrDeductStock(event);

			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

		} catch (Exception ex) {
			System.out.println("Consumer crashed: " + ex.getMessage());

			channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
		}
	}
}
