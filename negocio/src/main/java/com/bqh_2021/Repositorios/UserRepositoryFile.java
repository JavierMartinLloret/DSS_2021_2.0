package com.bqh_2021.Repositorios;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashSet;
import java.util.Set;

import com.bqh_2021.Entidades.Clases.User;
import com.bqh_2021.Utils.PropertiesReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class UserRepositoryFile {
    
    protected File f = new File(PropertiesReader.getInstance().getProperty("user.file"));
    protected static int idCounter = 0;

    private static final UserRepositoryFile SINGLE_INSTANCE = new UserRepositoryFile();
    
    public UserRepositoryFile(){}

    public static UserRepositoryFile getInstance(){
        return SINGLE_INSTANCE;
    }

    public int getCurrentID(){
        return ++idCounter;
    }

    public Set<User> GetUsers(){       
        Set<User> set = new HashSet<User>();
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(f)){
            Object obj = jsonParser.parse(reader);
            JSONArray usersArray = (JSONArray) obj;
            for(Object o: usersArray){
                JSONObject u = (JSONObject) o;
                try{
                    if(Integer.parseInt(u.get("userID").toString()) > idCounter){
                        idCounter = Integer.parseInt(u.get("userID").toString());
                    }
                    User user = new User(Integer.parseInt(u.get("userID").toString()),(String)u.get("nickname"), (String)u.get("email"), (String)u.get("password"), Boolean.parseBoolean(u.get("isAdult").toString()));
                    set.add(user);
                }catch(Exception e){}
            }  
        }catch(Exception e){}  
        return set;
    }

    public void PostUsers(Set<User> set){       
        JSONArray array = new JSONArray();
        try (FileWriter writer = new FileWriter(f)){
            for (User u: set){
                JSONObject user = new JSONObject();
                user.put("userID", u.getUserID());
                user.put("nickname", u.getNickname());
                user.put("email", u.getEmail());
                user.put("password", u.getPassword());
                user.put("isAdult", u.getIsAdult());
                array.add(user);
            }
            writer.write(array.toJSONString());
            writer.flush();
            writer.close();
        }catch(Exception e){}
    }
}
