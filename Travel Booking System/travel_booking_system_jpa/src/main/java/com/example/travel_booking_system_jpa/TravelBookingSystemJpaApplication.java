package com.example.travel_booking_system_jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TravelBookingSystemJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(TravelBookingSystemJpaApplication.class, args);
	}

}
