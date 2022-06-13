package com.shootfish.xyz.task.userRegistrationAndAuthentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@ComponentScan("com.shootfish.xyz.task.userRegistrationAndAuthentication")
public class UserRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserRestApplication.class, args);


	}
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/api/**").allowedOrigins("https://user-registration-app-frontend.herokuapp.com/");
				registry.addMapping("/**").allowedOrigins("https://user-registration-app-frontend.herokuapp.com/");
			}
		};
	}

}
