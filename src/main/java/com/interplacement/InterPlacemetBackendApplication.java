package com.interplacement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class InterPlacemetBackendApplication {

	public static void main(String[] args) {

		SpringApplication.run(InterPlacemetBackendApplication.class, args);
	}

}
