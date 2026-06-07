package com.example.messagingrabbitmq;

import java.util.concurrent.CountDownLatch;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

	public void receiveMessage(String message) {
		System.out.println("\n(Получено)" + message);
		System.out.print("> ");
	}

}
