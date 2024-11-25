package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.stereotype.Service;


// The @SpringBootApplication annotation marks this class as the main entry point for the Spring Boot application.
// The 'exclude = {DataSourceAutoConfiguration.class}' part tells Spring Boot to skip auto-configuring a datasource,
// because no database is connected yet.
/*@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})*/
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		// SpringApplication.run() launches the Spring Boot application, initializing the Spring context and starting the embedded server.
		SpringApplication.run(DemoApplication.class, args);
	}



}
