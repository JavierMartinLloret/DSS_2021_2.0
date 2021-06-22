package com.bqh_2021.Abstract_Factory_DAO.File_DAO;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bqh_2021.Abstract_Factory_DAO.Interfaces.IProductDAO;
import com.bqh_2021.Entidades.Clases.Menu;
import com.bqh_2021.Entidades.Clases.Product;
import com.bqh_2021.Entidades.Interfaces.IProduct;
import com.bqh_2021.Utils.PropertiesReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class FileProductDAO implements IProductDAO{

    protected static File f;
    protected static String product = PropertiesReader.getInstance().getProperty("product.file");
    
    
    public FileProductDAO(String kitchenEmail){
        f = new File(product + kitchenEmail + ".json");
    }

    public HashMap<String, List<IProduct>> getProducts(){       
        HashMap<String, List<IProduct>>  map = new HashMap<String, List<IProduct>>();
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(f)){
            Object obj = jsonParser.parse(reader);
            JSONArray productsArray = (JSONArray) obj;
            Set<Object> menus = new HashSet<Object>();
            IProduct product = new Product();
            for(Object o: productsArray){
                JSONObject p = (JSONObject) o;
                String category = (String)p.get("prodCategory");
                if(!map.containsKey(category)){
                    map.put(category, new ArrayList<IProduct>());
                }
                if(category.equals("Menu")){
                    menus.add(o);
                }
                else{
                    product = new Product((String)p.get("name"), new BigDecimal(p.get("price").toString()), ((Number)p.get("stock")).intValue(), category);
                    map.get(category).add(product);
                }
            }
            String category = "Menu";
            if(!map.containsKey(category)){
                map.put(category, new ArrayList<IProduct>());
            }
            for(Object o: menus){
                JSONObject j = (JSONObject) o;
                JSONObject items = (JSONObject)j.get("products");
                HashMap<IProduct, Integer> mapProd = new HashMap<IProduct, Integer>();
                IProduct itemAux = new Product();
                for(Object i: items.keySet()){
                    String prod = i.toString();
                    prod = prod.replaceAll("[()€]", "");
                    String[] pr = prod.split(" ");
                    int size = pr.length;
                    String name = "";
                    for(int d=0; d<size-1; d++){
                        name = name.concat(pr[d]);
                        name = name.concat(" ");
                    }
                    name = name.substring(0, name.length()-1);
                    for(String c: map.keySet()){
                        for(IProduct pro: map.get(c)){
                            if(pro.getName().equals(name)){
                                itemAux = pro;
                                break;
                            }
                        }
                    }
                    mapProd.put(itemAux, Integer.parseInt(items.get(i).toString()));
                }
                product = new Menu((String)j.get("name"), Float.parseFloat(j.get("discount").toString()), mapProd);
                map.get(category).add(product);
            }  
            reader.close();
        }catch(Exception e){}  
        return map;
    }

    public void postProducts(HashMap<String, List<IProduct>> map){
        JSONArray array = new JSONArray();
        try (FileWriter writer = new FileWriter(f)){
            for (List<IProduct> l: map.values()){
                for (IProduct p: l){
                    JSONObject product = new JSONObject();
                    Product pr = (Product)p;
                    if(pr.getCategory().equals("Menu")){
                        Menu m = (Menu)p;
                        product.put("discount", m.getDiscount());
                        product.put("products", m.getProducts());
                        product.put("name", m.getName());
                        product.put("prodCategory", "Menu");
                        array.add(m);
                    }
                    else{
                        product.put("name", pr.getName());
                        product.put("price", pr.getPrice());
                        product.put("stock", pr.getStock());
                        product.put("prodCategory", pr.getCategory());
                        array.add(product);
                    }
                }
            }
            writer.write(array.toJSONString());
            writer.flush();
            writer.close();
        }catch(Exception e){}
    }

}