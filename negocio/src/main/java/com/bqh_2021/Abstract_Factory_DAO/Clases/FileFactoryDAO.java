package com.bqh_2021.Abstract_Factory_DAO.Clases;

import com.bqh_2021.Abstract_Factory_DAO.File_DAO.FileCafeteriaDAO;
import com.bqh_2021.Abstract_Factory_DAO.File_DAO.FileCafeteriaIdDAO;
import com.bqh_2021.Abstract_Factory_DAO.File_DAO.FileDayBoxDAO;
import com.bqh_2021.Abstract_Factory_DAO.File_DAO.FileOrderDAO;
import com.bqh_2021.Abstract_Factory_DAO.File_DAO.FileProdCategoryDAO;
import com.bqh_2021.Abstract_Factory_DAO.File_DAO.FileProductDAO;
import com.bqh_2021.Abstract_Factory_DAO.File_DAO.FileUserDAO;
import com.bqh_2021.Abstract_Factory_DAO.File_DAO.FileUserSecurityDAO;
import com.bqh_2021.Abstract_Factory_DAO.Interfaces.ICafeteriaDAO;
import com.bqh_2021.Abstract_Factory_DAO.Interfaces.ICafeteriaIdDAO;
import com.bqh_2021.Abstract_Factory_DAO.Interfaces.IDayBoxDAO;
import com.bqh_2021.Abstract_Factory_DAO.Interfaces.IFactoryDAO;
import com.bqh_2021.Abstract_Factory_DAO.Interfaces.IOrderDAO;
import com.bqh_2021.Abstract_Factory_DAO.Interfaces.IProdCategoryDAO;
import com.bqh_2021.Abstract_Factory_DAO.Interfaces.IProductDAO;
import com.bqh_2021.Abstract_Factory_DAO.Interfaces.IUserDAO;
import com.bqh_2021.Abstract_Factory_DAO.Interfaces.IUserSecurityDAO;

public class FileFactoryDAO implements IFactoryDAO{

    @Override
    public IUserDAO createUserDAO() {
        return new FileUserDAO();
    }

    @Override
    public ICafeteriaDAO createCafeteriaDAO(String id) {
        return new FileCafeteriaDAO(id);
    }

    @Override
    public ICafeteriaIdDAO createCafeteriaIdDAO() {
        return new FileCafeteriaIdDAO();
    }

    @Override
    public IOrderDAO createOrderDAO(String id) {
        return new FileOrderDAO(id);
    }

    @Override
    public IProdCategoryDAO createProdCategoryDAO() {
        return new FileProdCategoryDAO();
    }

    @Override
    public IProductDAO createProductDAO(String kitchenEmail) {
        return new FileProductDAO(kitchenEmail);
    }

    @Override
    public IUserSecurityDAO createUserSecurityDAO() {
        return new FileUserSecurityDAO();
    }

    @Override
    public IDayBoxDAO createDayBoxDAO(){
        return new FileDayBoxDAO();
    }
    
}
