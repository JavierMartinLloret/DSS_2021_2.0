package com.BQH_2021.API_HTTP.Controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import com.BQH_2021.API_HTTP.ApiApplication;
import com.BQH_2021.API_HTTP.Servicios.SendEmailService;
import com.BQH_2021.Negocio.Entidades.Clases.Cafeteria;
import com.BQH_2021.Negocio.Entidades.Clases.Order;
import com.BQH_2021.Negocio.Entidades.Clases.OrderWithUserAndDate;
import com.BQH_2021.Negocio.Entidades.Clases.User;
import com.BQH_2021.Negocio.Entidades.Interfaces.IProduct;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class OrderController {

    @Autowired
    public SendEmailService sendEmailService;

    @GetMapping("/cafeteriaOrders")
    public Set<Order> GetOrdersByCafeteria(@RequestParam String cafeteria){
        Set<Order> s = new HashSet<Order>();
        for(Cafeteria c: ApiApplication.cafeterias){
            if(c.getKitchenEmail().equals(cafeteria)){
                return c.getSystem().getOrders();
            }
        }
        return s;
    }

    @GetMapping("/userOrders")
    public Map<String, Set<Order>> GetOrdersByUser(@RequestParam String user){
        Map<String, Set<Order>> map = new HashMap<String, Set<Order>>();
        for(Cafeteria c: ApiApplication.cafeterias){
            map.put(c.getKitchenEmail(), new HashSet<Order>());
            map.get(c.getKitchenEmail()).addAll(c.getArchivedOrdersFromUser(user));
        }
        return map;
    }

    @PostMapping("/orders")
    public String PostOrder(@RequestBody String payload) throws ParseException, java.text.ParseException{
        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(payload);
        JSONObject j = (JSONObject)obj;
        int orderID = 0;
        for(Cafeteria c: ApiApplication.cafeterias){
            if(c.getKitchenEmail().equals(j.get("cafeteria").toString())){
                DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
                User u = new User(j.get("user").toString());
                orderID = c.createNewOrder(u, dateFormat.parse(j.get("date").toString()));
                break;
            }
        }
        return "{\"orderID\": \"" + orderID + "\"}";
    }

    @PutMapping("/orders")
    public String PutOrder(@RequestBody String payload) throws ParseException{
        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(payload);
        JSONObject j = (JSONObject)obj;
        for(Cafeteria c: ApiApplication.cafeterias){
            if(c.getKitchenEmail().equals(j.get("cafeteria").toString())){
                JSONObject items = (JSONObject)j.get("order");
                int orderID = Integer.parseInt(j.get("orderID").toString());
                for(Object i: items.keySet()){
                    try {
                        IProduct p = c.getProductCafeteria(i.toString());
                        if(!Boolean.parseBoolean(j.get("exist").toString())){
                            try {
                                c.addToOrder(new User(j.get("user").toString()), orderID, p, Integer.parseInt(items.get(i.toString()).toString()));
                            } catch (RuntimeException rE) {
                                return "{\"status\": \"fail\",\"error\": \"" + rE + "\"}";
                            }
                        }
                        else{
                            try {
                                c.removeFromOrder(new User(j.get("user").toString()), orderID, p, Integer.parseInt(items.get(i.toString()).toString()));    
                            } catch (RuntimeException rE) {
                                return "{\"status\": \"fail\",\"error\": \"" + rE + "\"}";
                            }
                        }
                    } catch (RuntimeException rE) {
                        return "{\"status\": \"fail\",\"error\": \"" + rE + "\"}";
                    }
                }
                break;
            }
        }
        return "{\"status\": \"succes\"}";
    }

    @DeleteMapping("/orders")
    public String DeleteOrder(@RequestBody String payload) throws ParseException{
        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(payload);
        JSONObject j = (JSONObject)obj;
        for(Cafeteria c: ApiApplication.cafeterias){
            if(c.getKitchenEmail().equals(j.get("cafeteria").toString())){
                try {
                    User user = new User(j.get("user").toString());
                    int orderID = Integer.parseInt(j.get("orderID").toString());
                    OrderWithUserAndDate owad = c.getOWUADFromId(user, orderID);
                    sendEmailService.sendEmail("javier.martinlloret@alum.uca.es", c.getOpenedOrderFormID(user, orderID).getItems().toString(), owad.getDate().toString() + " " + owad.getClient().getEmail());
                    c.endOrder(user, orderID);
                } catch (RuntimeException rE) {
                    return "{\"status\": \"fail\",\"error\": \"" + rE + "\"}";
                }
            }
        }
        return "{\"status\": \"succes\"}";
    }

}
