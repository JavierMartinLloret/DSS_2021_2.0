package com.bqh_2021.Abstract_Factory_DAO.File_DAO;

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
