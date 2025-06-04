package com.abn_amro.recipemanagement;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@OpenAPIDefinition(
		info = @Info(
				title = "Recipes Management REST API Documentation",
				description = "Recipes Management microservice REST API Documentation",
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
				description =  "Recipes Management microservice REST API Documentation",
				url = "l"
		)
)
public class RecipemanagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecipemanagementApplication.class, args);
	}

}
