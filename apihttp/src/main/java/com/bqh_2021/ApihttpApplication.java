package com.bqh_2021;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bqh_2021.Clases.User;

@SpringBootApplication
@RestController
public class ApihttpApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApihttpApplication.class, args);
	}

	@GetMapping("/user")
	public String getUser(){
		return new User("juanca").getName();
	}

}
