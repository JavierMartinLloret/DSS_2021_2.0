package com.bqh_2021.Abstract_Factory_DAO.File_DAO;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import com.bqh_2021.Abstract_Factory_DAO.Interfaces.IDayBoxDAO;
import com.bqh_2021.Utils.PropertiesReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class FileDayBoxDAO implements IDayBoxDAO {

    protected static File f;
    protected static String product = PropertiesReader.getInstance().getProperty("dayBox.file");
    
    
    public FileDayBoxDAO(String kitchenEmail){
        f = new File(product + kitchenEmail + ".json");
    }

    @Override
    public Map<String, BigDecimal> getDayBox() {
        Map<String, BigDecimal> map = new HashMap<String, BigDecimal>();
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(f)){
            Object obj = jsonParser.parse(reader);
            JSONArray dayBox = (JSONArray) obj;
            for(Object o: dayBox){
                JSONObject j = (JSONObject) o;
                map.put(j.get("date").toString(), new BigDecimal(j.get("dayBox").toString()));
            }  
        }catch(Exception e){}  
        return map;
    }

    @Override
    public void postDayBox(Map<String, BigDecimal> map) {
        JSONArray array = new JSONArray();
        try (FileWriter writer = new FileWriter(f)){
            for (String u: map.keySet()){
                JSONObject j = new JSONObject();
                j.put("date", u.toString());
                j.put("dayBox", map.get(u));
                array.add(j);
            }
            writer.write(array.toJSONString());
            writer.flush();
            writer.close();
        }catch(Exception e){}
    }
    
}
