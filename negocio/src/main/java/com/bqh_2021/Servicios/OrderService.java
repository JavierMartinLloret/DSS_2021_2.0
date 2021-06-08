package com.bqh_2021.Servicios;

import java.util.Set;

import com.BQH_2021.Persistencia.Repositorios.OrderRespositoryFile;
import com.bqh_2021.Entidades.Clases.Order;

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
