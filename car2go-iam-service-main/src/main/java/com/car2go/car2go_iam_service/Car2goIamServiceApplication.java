package com.car2go.car2go_iam_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Car2goIamServiceApplication
 *
 * @summary
 * The main class of the Car2go IAM Service application.
 * It is responsible for starting the Spring Boot application.
 * It also enables JPA auditing.
 *
 * @since 1.0
 */
@EnableJpaAuditing
@SpringBootApplication
public class Car2goIamServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(Car2goIamServiceApplication.class, args);
	}

}
