package com.order_process.config;

import org.springframework.amqp.core.TopicExchange; 
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@Configuration
public class RabbitConfig {

	public static final String ORDER_EXCHANGE = "order.exchange";

	@Bean
	public TopicExchange orderExchange() {
		return new TopicExchange(ORDER_EXCHANGE);
	}

	@Bean
	public MessageConverter jsonMessageConverter() {
		return new JacksonJsonMessageConverter();
	}

	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
	    RabbitTemplate template = new RabbitTemplate(connectionFactory);
	    template.setMessageConverter(jsonMessageConverter());
	    return template;
	}
}
