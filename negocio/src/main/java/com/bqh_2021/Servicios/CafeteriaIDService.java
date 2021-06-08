package com.bqh_2021.Servicios;

import java.util.Set;

import com.bqh_2021.Entidades.Clases.Cafeteria;
import com.bqh_2021.Repositorios.CafeteriaIDRepositoryFile;


public class CafeteriaIDService {

    protected CafeteriaIDRepositoryFile repository;

    public CafeteriaIDService(){
        repository = CafeteriaIDRepositoryFile.getInstance();
    }
    
    public Set<Cafeteria> GetCafeterias(){
        return repository.GetCafeterias();
    }
}
