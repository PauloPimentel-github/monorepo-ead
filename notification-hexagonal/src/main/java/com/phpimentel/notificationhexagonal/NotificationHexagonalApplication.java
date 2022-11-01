package com.phpimentel.notificationhexagonal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class NotificationHexagonalApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationHexagonalApplication.class, args);
	}

}
