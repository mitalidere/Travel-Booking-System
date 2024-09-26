package com.example.destination_feignclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DestinationFeignclientApplication {

	public static void main(String[] args) {
		SpringApplication.run(DestinationFeignclientApplication.class, args);
	}

}
