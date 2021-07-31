package com.microservices.practice.registry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class NamingServiceRegistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(NamingServiceRegistryApplication.class, args);
	}

}
