package com.bqh_2021.Abstract_Factory_DAO.Clases;

import com.bqh_2021.Abstract_Factory_DAO.Interfaces.IFactoryDAO;
import com.bqh_2021.Abstract_Factory_DAO.Interfaces.IUserDAO;
import com.bqh_2021.File_DAO.FileUserDAO;

public class FileFactoryDAO implements IFactoryDAO{

    @Override
    public IUserDAO createUserDAO() {
        return new FileUserDAO();
    }
    
}
