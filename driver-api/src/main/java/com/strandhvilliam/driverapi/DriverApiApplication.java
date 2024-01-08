package com.strandhvilliam.driverapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class DriverApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DriverApiApplication.class, args);
	}

}
