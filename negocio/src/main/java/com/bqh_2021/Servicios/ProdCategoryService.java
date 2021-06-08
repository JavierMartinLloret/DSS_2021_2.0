package com.bqh_2021.Servicios;

import java.util.SortedSet;

import com.bqh_2021.Repositorios.ProdCategoryRepositoryFile;



public class ProdCategoryService {
    
    protected ProdCategoryRepositoryFile repository;

    public ProdCategoryService(){
        repository = ProdCategoryRepositoryFile.getInstance();
    }
    
    public SortedSet<String> GetCategories(){
        return repository.GetCategories();
    }
}
