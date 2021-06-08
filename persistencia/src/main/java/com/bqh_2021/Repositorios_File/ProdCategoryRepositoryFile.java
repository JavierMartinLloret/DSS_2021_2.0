package com.BQH_2021.Persistencia.Repositorios;


import java.io.File;
import java.io.FileReader;
import java.util.SortedSet;
import java.util.TreeSet;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ProdCategoryRepositoryFile{

    protected File f = new File("Proyecto/data/prodCategory.json");
    
    private static final ProdCategoryRepositoryFile SINGLE_INSTANCE = new ProdCategoryRepositoryFile();
    
    public ProdCategoryRepositoryFile(){}

    public static ProdCategoryRepositoryFile getInstance(){
        return SINGLE_INSTANCE;
    }

    public SortedSet<String> GetCategories(){       
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
