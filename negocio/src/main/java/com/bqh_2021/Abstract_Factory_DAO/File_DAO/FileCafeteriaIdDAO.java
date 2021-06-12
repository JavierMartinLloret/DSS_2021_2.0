package com.bqh_2021.Abstract_Factory_DAO.File_DAO;

import java.io.File;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;

import com.bqh_2021.Abstract_Factory_DAO.Interfaces.ICafeteriaIdDAO;
import com.bqh_2021.Entidades.Clases.Cafeteria;
import com.bqh_2021.Utils.PropertiesReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class FileCafeteriaIdDAO implements ICafeteriaIdDAO{
    
    protected File f = new File(PropertiesReader.getInstance().getProperty("cafeteriaID.file"));

    private static final FileCafeteriaIdDAO SINGLE_INSTANCE = new FileCafeteriaIdDAO();
    
    public FileCafeteriaIdDAO(){}

    public static FileCafeteriaIdDAO getInstance(){
        return SINGLE_INSTANCE;
    }

    public Set<Cafeteria> getCafeterias(){       
        Set<Cafeteria> set= new HashSet<Cafeteria>();
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(f)){
            Object obj = jsonParser.parse(reader);
            JSONArray productsArray = (JSONArray) obj;
            for(Object o: productsArray){
                JSONObject p = (JSONObject) o;
                set.add(new Cafeteria((String)p.get("id")));
            }  
            reader.close();
        }catch(Exception e){}  
        return set;
    }
}
