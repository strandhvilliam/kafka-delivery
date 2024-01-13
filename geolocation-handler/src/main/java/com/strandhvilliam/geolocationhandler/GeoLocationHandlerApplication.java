package com.strandhvilliam.geolocationhandler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class GeoLocationHandlerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GeoLocationHandlerApplication.class, args);
	}

}
