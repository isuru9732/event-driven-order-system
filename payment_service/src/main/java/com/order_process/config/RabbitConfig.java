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

	public static final String ORDER_EXCHANGE = "order.exchange";
	public static final String ORDER_CREATED = "order.created";
	public static final String PAYMENT_QUEUE = "payment.queue";
	public static final String PAYMENT_DLQ = "payment.dlq";
	public static final String PAYMENT_EXCHANGE = "payment.exchange";

    @Bean
    TopicExchange orderExchange() {
		return new TopicExchange(ORDER_EXCHANGE);
	}
    
    @Bean
    TopicExchange paymentExchange() {
		return new TopicExchange(PAYMENT_EXCHANGE);
	}

	@Bean
	Queue paymentQueue() {
		return QueueBuilder.durable(PAYMENT_QUEUE)
				.withArgument("x-dead-letter-exchange", "")
				.withArgument("x-dead-letter-routing-key", PAYMENT_DLQ)
				.build();
	}

	@Bean
	Queue paymentDeadLetterQueue() {
		return QueueBuilder.durable(PAYMENT_DLQ).build();
	}

	@Bean
	Binding paymentBinding(Queue paymentQueue, TopicExchange orderExchange) {
		return BindingBuilder.bind(paymentQueue).to(orderExchange).with(ORDER_CREATED);
	}
	
	@Bean
	public MessageConverter jsonMessageConverter() {
		return new JacksonJsonMessageConverter();
	}

}
