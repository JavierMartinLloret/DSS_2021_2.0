package com.bqh_2021.Abstract_Factory_DAO.File_DAO;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.bqh_2021.Abstract_Factory_DAO.Interfaces.IOrderDAO;
import com.bqh_2021.Entidades.Clases.Order;
import com.bqh_2021.Entidades.Clases.Product;
import com.bqh_2021.Entidades.Interfaces.IProduct;
import com.bqh_2021.Utils.PropertiesReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;



public class FileOrderDAO implements IOrderDAO{

    protected File f;
    protected static String order = PropertiesReader.getInstance().getProperty("order.file");
    
    public FileOrderDAO(String id){
        f = new File(order + id + ".json");
    }

    @Override
    public Set<Order> getOrders() {
        Set<Order> set = new HashSet<Order>();
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(f)){
            Object obj = jsonParser.parse(reader);
            JSONArray ordersArray = (JSONArray) obj;
            for(Object o: ordersArray){
                JSONObject p = (JSONObject) o;
                JSONObject items = (JSONObject)p.get("orderItems");
                HashMap<IProduct, Integer> map = new HashMap<IProduct, Integer>();
                for(Object i: items.keySet()){
                    String product = i.toString();
                    product = product.replaceAll("[()€]", "");
                    String[] pr = product.split(" ");
                    String name = "";
                    for(int j = 0; j<pr.length-1; j++){
                        name = name + pr[j] + " ";
                    }
                    Product prod = new Product(name, new BigDecimal(pr[pr.length-1].toString()));
                    map.put(prod, Integer.parseInt(items.get(i).toString()));
                }
                Order or = new Order(((Number)p.get("id")).intValue(), new BigDecimal(p.get("price").toString()), map);
                set.add(or);
                reader.close();
            }  
        }catch(Exception e){}  
        return set;
    }

    @Override
    public void postOrders(Set<Order> set) {
        JSONArray array = new JSONArray();
        try (FileWriter writer = new FileWriter(f)){
            for (Order o: set){
                JSONObject order = new JSONObject();
                order.put("id", o.getId());
                order.put("price", o.getPrice());
                order.put("orderItems", o.getItems());
                array.add(order);
            }
            writer.write(array.toJSONString());
            writer.flush();
            writer.close();
        }catch(Exception e){}
    }

    
}
