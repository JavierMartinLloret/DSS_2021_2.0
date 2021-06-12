package com.bqh_2021.Abstract_Factory_DAO.File_DAO;


import java.io.File;
import java.io.FileReader;
import java.util.SortedSet;
import java.util.TreeSet;

import com.bqh_2021.Abstract_Factory_DAO.Interfaces.IProdCategoryDAO;
import com.bqh_2021.Utils.PropertiesReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class FileProdCategoryDAO implements IProdCategoryDAO{

    protected File f = new File(PropertiesReader.getInstance().getProperty("prodCategory.file"));
    
    private static final FileProdCategoryDAO SINGLE_INSTANCE = new FileProdCategoryDAO();
    
    public FileProdCategoryDAO(){}

    public static FileProdCategoryDAO getInstance(){
        return SINGLE_INSTANCE;
    }

    public SortedSet<String> getCategories(){       
        SortedSet<String> set= new TreeSet<String>();
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(f)){
            Object obj = jsonParser.parse(reader);
            JSONArray productsArray = (JSONArray) obj;
            for(Object o: productsArray){
                JSONObject p = (JSONObject) o;
                set.add((String)p.get("name"));
            }  
            reader.close();
        }catch(Exception e){}  
        return set;
    }

}
