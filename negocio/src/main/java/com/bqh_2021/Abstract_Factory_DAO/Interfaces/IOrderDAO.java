package com.bqh_2021.Abstract_Factory_DAO.Interfaces;

import java.util.Set;

import com.bqh_2021.Entidades.Clases.Order;

//TODO:DOCUMENTAR
public interface IOrderDAO {
    public Set<Order> GetOrders();
    public void PostOrders(Set<Order> set);
}
