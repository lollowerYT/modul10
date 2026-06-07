package com.example.messagingrabbitmq;

import java.util.concurrent.TimeUnit;

import java.util.Scanner;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {

	private final RabbitTemplate rabbitTemplate;

	public Runner(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("--- Чат запущен. Введите сообщение и нажмите Enter ---");
		System.out.println("Для выхода введите 'exit'");

		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.print("> ");
			String message = scanner.nextLine();
			if ("exit".equalsIgnoreCase(message.trim())) {
				break;
			}
			if (!message.trim().isEmpty()) {
				rabbitTemplate.convertAndSend(MessagingRabbitmqApplication.fanoutExchangeName, "", message);
				System.out.println("[Отправлено] " + message);
			}
		}
		scanner.close();
		System.out.println("Завершение работы.");
		System.exit(0);
	}

}
