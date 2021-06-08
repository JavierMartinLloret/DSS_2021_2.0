package com.bqh_2021.Repositorios;

import java.io.File;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;

import com.bqh_2021.Entidades.Clases.Cafeteria;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class CafeteriaIDRepositoryFile {
    
    protected File f = new File("data/cafeterias.json");

    private static final CafeteriaIDRepositoryFile SINGLE_INSTANCE = new CafeteriaIDRepositoryFile();
    
    public CafeteriaIDRepositoryFile(){}

    public static CafeteriaIDRepositoryFile getInstance(){
        return SINGLE_INSTANCE;
    }

    public Set<Cafeteria> GetCafeterias(){       
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
