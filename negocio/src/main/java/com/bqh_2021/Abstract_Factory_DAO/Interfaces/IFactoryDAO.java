package com.bqh_2021.Abstract_Factory_DAO.Interfaces;

public interface IFactoryDAO {
    public ICafeteriaDAO createCafeteriaDAO(String id);
    public ICafeteriaIdDAO createCafeteriaIdDAO();
    public IOrderDAO createOrderDAO(String id);
    public IProdCategoryDAO createProdCategoryDAO();
    public IProductDAO createProductDAO(String kitchenEmail);
    public IUserDAO createUserDAO();
    public IUserSecurityDAO createUserSecurityDAO();
    
}
