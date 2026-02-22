package com.agrosoft.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AgroSoftApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgroSoftApplication.class, args);
	}

}
