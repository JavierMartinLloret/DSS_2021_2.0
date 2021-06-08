package com.bqh_2021.Servicios;

import java.util.HashMap;
import java.util.List;

import com.bqh_2021.Entidades.Interfaces.IProduct;
import com.bqh_2021.Repositorios.ProductRepositoryFile;


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