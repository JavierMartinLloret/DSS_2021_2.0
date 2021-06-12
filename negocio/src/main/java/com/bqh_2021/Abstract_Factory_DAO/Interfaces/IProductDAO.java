package com.bqh_2021.Abstract_Factory_DAO.Interfaces;

import java.util.HashMap;
import java.util.List;

import com.bqh_2021.Entidades.Interfaces.IProduct;

public interface IProductDAO {
    public HashMap<String, List<IProduct>> getProducts();
    public void postProducts(HashMap<String, List<IProduct>> map);
}
