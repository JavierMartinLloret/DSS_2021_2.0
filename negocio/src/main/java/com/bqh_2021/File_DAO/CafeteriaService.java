package com.bqh_2021.File_DAO;

import java.util.List;
import java.util.Map;

import com.bqh_2021.Entidades.Clases.OrderWithUserAndDate;
import com.bqh_2021.Repositorios.CafeteriaRepositoryFile;


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
