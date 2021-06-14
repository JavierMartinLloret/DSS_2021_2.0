package com.bqh_2021.Utils;

import com.bqh_2021.Abstract_Factory_DAO.Clases.FileFactoryDAO;
import com.bqh_2021.Abstract_Factory_DAO.Interfaces.IFactoryDAO;

public class PersistenceConfiguration {

    public static IFactoryDAO LoadPersistenceType() throws RuntimeException
    {//TODO: Añadir persistencia SQL
        if(PropertiesReader.getInstance().getProperty("persistence.type").equals("file"))
            return new FileFactoryDAO();
        else
            throw new RuntimeException("Error, no se ha encontrado el tipo de persistencia");
    }
}
