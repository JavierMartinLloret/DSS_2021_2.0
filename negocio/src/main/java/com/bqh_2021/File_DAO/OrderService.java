package com.bqh_2021.File_DAO;

import java.util.Set;

import com.bqh_2021.Entidades.Clases.Order;
import com.bqh_2021.Repositorios.OrderRespositoryFile;



public class OrderService {

    protected OrderRespositoryFile repository;

    public OrderService(String kitchenEmail){
        repository = new OrderRespositoryFile(kitchenEmail);
    }
    
    public Set<Order> GetOrders(){
        return repository.GetOrders();
    }

    public void PostOrders(Set<Order> o){
        repository.PostOrders(o);
    }
}
