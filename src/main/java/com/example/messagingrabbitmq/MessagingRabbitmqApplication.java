package com.example.messagingrabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.AnonymousQueue;

@SpringBootApplication
public class MessagingRabbitmqApplication {

	static final String fanoutExchangeName = "chat.exchange"; //new

	@Bean
	Queue autoDeleteQueue() {
		return new AnonymousQueue();
	}

	@Bean
	FanoutExchange  exchange() {
		return new FanoutExchange(fanoutExchangeName);
	}

	@Bean
	Binding binding(Queue autoDeleteQueue, FanoutExchange  exchange) {
		return BindingBuilder.bind(autoDeleteQueue).to(exchange);
	}

	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
			MessageListenerAdapter listenerAdapter, Queue autoDeleteQueue) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(autoDeleteQueue.getName());
		container.setMessageListener(listenerAdapter);
		return container;
	}

	@Bean
	MessageListenerAdapter listenerAdapter(Receiver receiver) {
		return new MessageListenerAdapter(receiver, "receiveMessage");
	}

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(MessagingRabbitmqApplication.class, args);
	}

}
