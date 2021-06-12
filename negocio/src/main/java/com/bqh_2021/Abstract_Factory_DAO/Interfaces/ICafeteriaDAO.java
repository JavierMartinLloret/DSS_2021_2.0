package com.bqh_2021.Abstract_Factory_DAO.Interfaces;

import java.util.List;
import java.util.Map;

import com.bqh_2021.Entidades.Clases.OrderWithUserAndDate;

public interface ICafeteriaDAO {
    public Map<String, List<OrderWithUserAndDate>> getOrders();
    public void postOrders(Map<String, List<OrderWithUserAndDate>> map);
}
