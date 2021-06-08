package com.bqh_2021.Servicios;

import java.util.HashMap;
import java.util.List;

import com.BQH_2021.Persistencia.Repositorios.ProductRepositoryFile;
import com.bqh_2021.Entidades.Interfaces.IProduct;

public class ProductService {

    protected ProductRepositoryFile repository;

    public ProductService(String kitchenEmail){
        repository = new ProductRepositoryFile(kitchenEmail);
    }
    
    public HashMap<String, List<IProduct>> GetProducts(){
        return repository.GetProducts();
    }

    public void PostProducts(HashMap<String, List<IProduct>> map){
        repository.PostProducts(map);
    }

}