package com.bqh_2021.Abstract_Factory_DAO.Clases;

import com.bqh_2021.Abstract_Factory_DAO.File_DAO.FileUserDAO;
import com.bqh_2021.Abstract_Factory_DAO.Interfaces.IFactoryDAO;
import com.bqh_2021.Abstract_Factory_DAO.Interfaces.IUserDAO;

public class FileFactoryDAO implements IFactoryDAO{

    @Override
    public IUserDAO createUserDAO() {
        return new FileUserDAO();
    }
    
}
