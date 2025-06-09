package com.abn_amro.usermanagment;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableFeignClients
//@EnableDiscoveryClient
@OpenAPIDefinition(
		info = @Info(
				title = "User Management REST API Documentation",
				description = "User Management microservice REST API Documentation",
				version = "v1",
				contact = @Contact(
						name = "Samson Oluwaseyi",
						email = "samson.oluwaseyi@capgemini.com",
						url = ""
				),
				license = @License(
						name = "Apache 2.0",
						url = ""
				)
		),
		externalDocs = @ExternalDocumentation(
				description =  "User Management microservice REST API Documentation",
				url = "l"
		)
)

public class UsermanagmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsermanagmentApplication.class, args);
	}

}
























































































