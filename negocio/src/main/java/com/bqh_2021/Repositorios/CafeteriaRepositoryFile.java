package com.bqh_2021.Repositorios;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.bqh_2021.Entidades.Clases.Cafeteria;
import com.bqh_2021.Entidades.Clases.OrderWithUserAndDate;
import com.bqh_2021.Entidades.Clases.User;
import com.bqh_2021.Utils.PropertiesReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class CafeteriaRepositoryFile {
    
    protected File f;

    protected static String cafeteria = PropertiesReader.getInstance().getProperty("cafeteria.file");
    
    public CafeteriaRepositoryFile(String id){
        f = new File(cafeteria + id + ".json");
    }

    public Map<String, List<OrderWithUserAndDate>> GetOrders(){       
        Map<String, List<OrderWithUserAndDate>> map = new HashMap<String, List<OrderWithUserAndDate>>();
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(f)){
            Object obj = jsonParser.parse(reader);
            JSONArray orders = (JSONArray) obj;
            for(Object o: orders){
                JSONObject p = (JSONObject) o;
                List<OrderWithUserAndDate> l = new ArrayList<OrderWithUserAndDate>();
                JSONArray order = (JSONArray)p.get("orders");
                for(Object i: order){
                    JSONObject j = (JSONObject)i;
                    DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
                    OrderWithUserAndDate or = new OrderWithUserAndDate(new User(j.get("client").toString()), new Cafeteria(j.get("placeOfOrder").toString(), true), dateFormat.parse(j.get("date").toString()), Integer.parseInt(j.get("orderID").toString()));
                    l.add(or);    
                }
                map.put(p.get("user").toString(), l);
            }  
        }catch(Exception e){}  
        return map;
    }

    public void PostOrders(Map<String, List<OrderWithUserAndDate>> map){
        JSONArray array = new JSONArray();
        try (FileWriter writer = new FileWriter(f)){
            for (String s: map.keySet()){
                JSONObject j = new JSONObject();
                JSONArray array2 = new JSONArray();
                for(OrderWithUserAndDate o: map.get(s)){
                    JSONObject k = new JSONObject();
                    k.put("orderID", o.getOrderID());
                    k.put("client", o.getClient().getEmail());
                    k.put("placeOfOrder", o.getPlaceOfOrder().getKitchenEmail());
                    k.put("date", o.getDate().toString());
                    array2.add(k);
                }
                j.put("user", s);
                j.put("orders", array2);
                array.add(j);
            }
            writer.write(array.toJSONString());
            writer.flush();
            writer.close();
        }catch(Exception e){}
    }

}
