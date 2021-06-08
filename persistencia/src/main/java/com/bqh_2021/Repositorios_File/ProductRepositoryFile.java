package com.BQH_2021.Persistencia.Repositorios;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.BQH_2021.Negocio.Entidades.Interfaces.IProduct;
import com.BQH_2021.Negocio.Entidades.Clases.Menu;
import com.BQH_2021.Negocio.Entidades.Clases.Product;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ProductRepositoryFile{

    protected static File f;
    protected static String product = "Proyecto/data/product-";
    
    
    public ProductRepositoryFile(String kitchenEmail){
        f = new File(product + kitchenEmail + ".json");
    }

    public HashMap<String, List<IProduct>> GetProducts(){       
        HashMap<String, List<IProduct>>  map = new HashMap<String, List<IProduct>>();
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(f)){
            Object obj = jsonParser.parse(reader);
            JSONArray productsArray = (JSONArray) obj;
            for(Object o: productsArray){
                JSONObject p = (JSONObject) o;
                String category = (String)p.get("prodCategory");
                IProduct product;
                if(category.equals("Menu")){
                    JSONObject items = (JSONObject)p.get("products");
                    HashMap<IProduct, Integer> mapProd = new HashMap<IProduct, Integer>();
                    Product itemAux;
                    for(Object i: items.keySet()){
                        String prod = i.toString();
                        prod = prod.replaceAll("[()€]", "");
                        String[] pr = prod.split(" ");
                        int size = pr.length;
                        String name = "";
                        for(int j=0; j<size-1; j++){
                            name = name.concat(pr[j]);
                            name = name.concat(" ");
                        }
                        itemAux = new Product(name, new BigDecimal(pr[size-1].toString()));
                        mapProd.put(itemAux, Integer.parseInt(items.get(i).toString()));
                    }
                    product = new Menu((String)p.get("name"), Float.parseFloat(p.get("discount").toString()), mapProd);
                }
                else{
                    product = new Product((String)p.get("name"), new BigDecimal(p.get("price").toString()), ((Number)p.get("stock")).intValue(), category);
                }
                if(!map.containsKey(category)){
                    map.put(category, new ArrayList<IProduct>());
                }
                map.get(category).add(product);
            }  
            reader.close();
        }catch(Exception e){}  
        return map;
    }

    public void PostProducts(HashMap<String, List<IProduct>> map){
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