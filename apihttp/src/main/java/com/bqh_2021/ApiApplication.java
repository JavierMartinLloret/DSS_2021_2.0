package com.bqh_2021;

import java.util.HashSet;
import java.util.Set;

import com.bqh_2021.Abstract_Factory_DAO.File_DAO.CafeteriaIDService;
import com.bqh_2021.Entidades.Clases.Cafeteria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ApiApplication {
	
	public static Set<Cafeteria> cafeterias = new HashSet<Cafeteria>();

	public static void main(String[] args) {
		CafeteriaIDService cafeteriaService = new CafeteriaIDService();
		cafeterias = cafeteriaService.GetCafeterias();
		SpringApplication.run(ApiApplication.class, args);
	}
}
