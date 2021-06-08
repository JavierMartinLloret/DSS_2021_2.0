package com.bqh_2021.Servicios;

import java.util.Set;

import com.BQH_2021.Persistencia.Repositorios.UserRepositoryFile;
import com.bqh_2021.Entidades.Clases.User;

public class UserService {

    protected UserRepositoryFile repository;

    public UserService(){
        repository = UserRepositoryFile.getInstance();
    }

    public int getCurrentID(){
        return repository.getCurrentID();
    }
    
    public Set<User> GetUsers(){
        return repository.GetUsers();
    }

    public void PostUsers(Set<User> set){
        repository.PostUsers(set);
    }
    
}
