package com.abn_amro.usermanagment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;

@SpringBootApplication
@EnableJdbcAuditing
public class UsermanagmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsermanagmentApplication.class, args);
	}

}
