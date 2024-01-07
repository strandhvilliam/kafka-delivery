package com.strandhvilliam.driveravailability;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class DriverAvailabilityApplication {

	public static void main(String[] args) {
		SpringApplication.run(DriverAvailabilityApplication.class, args);
	}

}
