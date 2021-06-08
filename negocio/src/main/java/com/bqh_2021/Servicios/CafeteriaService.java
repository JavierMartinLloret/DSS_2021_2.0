package com.bqh_2021.Servicios;

import java.util.List;
import java.util.Map;

import com.BQH_2021.Persistencia.Repositorios.CafeteriaRepositoryFile;
import com.bqh_2021.Entidades.Clases.OrderWithUserAndDate;

public class CafeteriaService {
    
    protected CafeteriaRepositoryFile repository;

    public CafeteriaService(String id){
        repository = new CafeteriaRepositoryFile(id);
    }
    
    public Map<String, List<OrderWithUserAndDate>> GetOrders(){
        return repository.GetOrders();
    }

    public void PostOrders(Map<String, List<OrderWithUserAndDate>> map){
        try {
            repository.PostOrders(map);
        }catch(Exception e){}
    }
}
