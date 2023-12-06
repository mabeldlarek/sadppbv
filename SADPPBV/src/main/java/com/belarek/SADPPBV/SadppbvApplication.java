package com.belarek.SADPPBV;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.Scanner;

@SpringBootApplication
@EnableWebSocket
@EnableWebSocketMessageBroker
@CrossOrigin(origins = "*") public class SadppbvApplication {

	@Value("${server.port}")
	private static int serverPort;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Informe a porta do servidor: ");
		serverPort = sc.nextInt();
		System.setProperty("server.port", String.valueOf(serverPort));
		SpringApplication.run(SadppbvApplication.class, args);
	}

	@Configuration
	public static class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

		@Override
		public void configureMessageBroker(MessageBrokerRegistry config) {
			config.enableSimpleBroker("/topic");
			config.setApplicationDestinationPrefixes("/app");
		}

		@Override
		public void registerStompEndpoints(StompEndpointRegistry registry) {
			registry.addEndpoint("/websocket").withSockJS();
		}

	}
}
