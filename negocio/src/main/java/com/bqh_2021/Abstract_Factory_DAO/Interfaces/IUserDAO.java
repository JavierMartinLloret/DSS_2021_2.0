package com.bqh_2021.Abstract_Factory_DAO.Interfaces;

import java.util.Set;

import com.bqh_2021.Entidades.Clases.User;

public interface IUserDAO {

    public int getNextUserID();
    public Set<User> getUsers();
    public void postUsers(Set<User> set);
    
}