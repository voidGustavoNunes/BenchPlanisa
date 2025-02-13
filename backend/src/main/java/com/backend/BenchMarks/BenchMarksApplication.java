package com.backend.BenchMarks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class BenchMarksApplication {

	public static void main(String[] args) {
			Dotenv dotenv = Dotenv.load();
			System.setProperty("SPRING_DATASOURCE_URL", dotenv.get("SPRING_DATASOURCE_URL"));
			System.setProperty("SPRING_DATASOURCE_USERNAME", dotenv.get("SPRING_DATASOURCE_USERNAME"));
			System.setProperty("SPRING_DATASOURCE_PASSWORD", dotenv.get("SPRING_DATASOURCE_PASSWORD"));
			System.setProperty("API_TOKEN", dotenv.get("API_TOKEN"));
			System.setProperty("MYSQL_DATABASE", dotenv.get("MYSQL_DATABASE"));
			System.setProperty("MYSQL_ROOT_PASSWORD", dotenv.get("MYSQL_ROOT_PASSWORD"));
			System.setProperty("MYSQL_USER", dotenv.get("MYSQL_USER"));
			System.setProperty("MYSQL_PASSWORD", dotenv.get("MYSQL_PASSWORD"));
			System.setProperty("API_URL", dotenv.get("API_URL"));

		SpringApplication.run(BenchMarksApplication.class, args);
	}

}
