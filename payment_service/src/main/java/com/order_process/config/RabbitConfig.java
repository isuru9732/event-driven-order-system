package com.order_process.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

	public static final String ORDER_EXCHANGE = "order.exchange";
	public static final String PAYMENT_QUEUE = "payment.queue";
	public static final String PAYMENT_DLQ = "payment.dlq";

	@Bean
	public Queue paymentQueue() {
		return QueueBuilder.durable(PAYMENT_QUEUE)
				.withArgument("x-dead-letter-exchange", "")
				.withArgument("x-dead-letter-routing-key", PAYMENT_DLQ)
				.build();
	}

	@Bean
	public Queue paymentDeadLetterQueue() {
		return QueueBuilder.durable(PAYMENT_DLQ).build();
	}

	@Bean
	public Binding paymentBinding(Queue paymentQueue, TopicExchange orderExchange) {
		return BindingBuilder.bind(paymentQueue).to(orderExchange).with("order.created");
	}

}
