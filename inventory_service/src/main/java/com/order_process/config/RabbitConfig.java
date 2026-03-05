package com.order_process.config;
 
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
	
	public static final String PAYMENT_EXCHANGE = "payment.exchange";
	public static final String INVENTORY_EXCHANGE = "inventory.exchange";
	public static final String PAYMENT_COMPLETED = "payment.completed";
	public static final String INVENTORY_QUEUE = "inventory.queue";
	public static final String INVENTORY_DLQ = "inventory.dlq";
	public static final String INVENTORY_UPDATED = "inventory.updated";


    @Bean
    TopicExchange paymentExchange() {
		return new TopicExchange(PAYMENT_EXCHANGE);
	}
    @Bean
    TopicExchange invoiceExchange() {
		return new TopicExchange(INVENTORY_EXCHANGE);
	}

	@Bean
	Queue inventoryQueue() {
		return QueueBuilder.durable(INVENTORY_QUEUE)
				.withArgument("x-dead-letter-exchange", "")
				.withArgument("x-dead-letter-routing-key", INVENTORY_DLQ)
				.build();
	}

	@Bean
	Queue inventoryDeadLetterQueue() {
		return QueueBuilder.durable(INVENTORY_DLQ).build();
	}

	@Bean
	Binding inventoryBinding(Queue inventoryQueue, TopicExchange paymentExchange) {
		return BindingBuilder.bind(inventoryQueue).to(paymentExchange).with(PAYMENT_COMPLETED);
	}
	
	@Bean
	public MessageConverter jsonMessageConverter() {
		return new JacksonJsonMessageConverter();
	}

}
