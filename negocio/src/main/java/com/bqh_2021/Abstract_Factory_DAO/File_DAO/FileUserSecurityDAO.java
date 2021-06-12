package com.bqh_2021.Abstract_Factory_DAO.File_DAO;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.bqh_2021.Abstract_Factory_DAO.Interfaces.IUserSecurityDAO;
import com.bqh_2021.Utils.PropertiesReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class FileUserSecurityDAO implements IUserSecurityDAO{

    protected File f = new File(PropertiesReader.getInstance().getProperty("userSecurity.file"));
    
    private static final FileUserSecurityDAO SINGLE_INSTANCE = new FileUserSecurityDAO();
    
    public FileUserSecurityDAO(){}

    public static FileUserSecurityDAO getInstance(){
        return SINGLE_INSTANCE;
    }

    public void postUserSecurityKey(Map<String, SecretKey> user_key){       
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

    public Map<String, SecretKey> getUserSecurityKeys(){       
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
