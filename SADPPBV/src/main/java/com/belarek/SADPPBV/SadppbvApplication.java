package com.belarek.SADPPBV;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Scanner;

@SpringBootApplication
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
}
