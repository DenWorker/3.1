package ru.DenWorker.PP_3_1_SpringBoot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebSpringBootApplication {

	// git check
	//http://localhost:8080/users
	public static void main(String[] args) {
		SpringApplication.run(WebSpringBootApplication.class, args);
		System.out.println("Hello world");
	}

}
