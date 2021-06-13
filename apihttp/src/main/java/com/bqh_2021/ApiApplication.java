package com.bqh_2021;

import java.util.HashSet;
import java.util.Set;

import com.bqh_2021.Abstract_Factory_DAO.Interfaces.ICafeteriaIdDAO;
import com.bqh_2021.Abstract_Factory_DAO.Interfaces.IFactoryDAO;
import com.bqh_2021.Entidades.Clases.Cafeteria;
import com.bqh_2021.Utils.PersistenceConfiguration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ApiApplication {
	
	public static Set<Cafeteria> cafeterias = new HashSet<Cafeteria>();


	public static void main(String[] args) {
		IFactoryDAO factoryDAO = PersistenceConfiguration.SelectPersistenceType();
		ICafeteriaIdDAO cafeteriaDAO = factoryDAO.createCafeteriaIdDAO();

		cafeterias = cafeteriaDAO.getCafeterias();
		SpringApplication.run(ApiApplication.class, args);
	}
}
