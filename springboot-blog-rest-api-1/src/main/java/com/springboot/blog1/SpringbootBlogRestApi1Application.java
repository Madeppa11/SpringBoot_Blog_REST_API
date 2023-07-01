package com.springboot.blog1;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringbootBlogRestApi1Application {
	
	@Bean
	public ModelMapper modelMapper() {
		
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringbootBlogRestApi1Application.class, args);
		System.out.println("Application blog started................");
	}

}
