package com.shootfish.xyz.task.userRegistrationAndAuthentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.shootfish.xyz.task.userRegistrationAndAuthentication")
public class UserRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserRestApplication.class, args);
	}

}
