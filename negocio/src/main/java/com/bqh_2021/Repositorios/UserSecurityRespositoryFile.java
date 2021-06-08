package com.bqh_2021.Repositorios;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class UserSecurityRespositoryFile {

    protected File f = new File("data/userSecurity.json");
    
    private static final UserSecurityRespositoryFile SINGLE_INSTANCE = new UserSecurityRespositoryFile();
    
    public UserSecurityRespositoryFile(){}

    public static UserSecurityRespositoryFile getInstance(){
        return SINGLE_INSTANCE;
    }

    public void PostUserSecurityKey(Map<String, SecretKey> user_key){       
        JSONArray array = new JSONArray();
        try (FileWriter writer = new FileWriter(f)){
            for (String u: user_key.keySet()){
                JSONObject j = new JSONObject();
                j.put("email", u.toString());
                j.put("secretKey", Base64.getEncoder().encodeToString(user_key.get(u).getEncoded()));
                array.add(j);
            }
            writer.write(array.toJSONString());
            writer.flush();
            writer.close();
        }catch(Exception e){}
    }

    public Map<String, SecretKey> GetUserSecurityKeys(){       
        Map<String, SecretKey> map = new HashMap<String, SecretKey>();
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(f)){
            Object obj = jsonParser.parse(reader);
            JSONArray userKeysArray = (JSONArray) obj;
            for(Object o: userKeysArray){
                JSONObject uk = (JSONObject) o;
                try{
                    byte[] decodedKey = Base64.getDecoder().decode(uk.get("secretKey").toString());
                    map.put(uk.get("email").toString(), new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES"));
                }catch(Exception e){}
            }  
        }catch(Exception e){}  
        return map;
    }
}
