package com.bqh_2021.Abstract_Factory_DAO.File_DAO;

import java.util.Map;

import javax.crypto.SecretKey;

import com.bqh_2021.Repositorios.UserSecurityRespositoryFile;


public class UserSecurityService {
    
    protected UserSecurityRespositoryFile repository;

    public UserSecurityService(){
        repository = UserSecurityRespositoryFile.getInstance();
    }
    
    public Map<String, SecretKey> GetUsersKeys(){
        return repository.GetUserSecurityKeys();
    }

    public void PostUsersKeys(Map<String, SecretKey> user_key){
        repository.PostUserSecurityKey(user_key);
    }
}
