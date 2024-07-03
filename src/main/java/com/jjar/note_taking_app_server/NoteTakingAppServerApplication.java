package com.jjar.note_taking_app_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class NoteTakingAppServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(NoteTakingAppServerApplication.class, args);
	}

}
