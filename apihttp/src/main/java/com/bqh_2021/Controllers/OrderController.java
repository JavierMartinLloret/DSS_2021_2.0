package com.bqh_2021.Controllers;

import java.math.BigDecimal;
import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.bqh_2021.ApiApplication;
import com.bqh_2021.Entidades.Clases.Cafeteria;
import com.bqh_2021.Entidades.Clases.Order;
import com.bqh_2021.Entidades.Clases.OrderWithUserAndDate;
import com.bqh_2021.Entidades.Clases.User;
import com.bqh_2021.Entidades.Interfaces.IProduct;
import com.bqh_2021.Servicio.SendEmailService;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import springfox.documentation.spring.web.json.Json;



@RestController
public class OrderController {

    @Autowired
    public SendEmailService sendEmailService;

    @Value("${spring.mail.ipPagos}")
    String apipagos;

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
    public String closeOrder(@RequestBody String payload) throws ParseException{
        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(payload);
        JSONObject j = (JSONObject)obj;
        for(Cafeteria c: ApiApplication.cafeterias){
            if(c.getKitchenEmail().equals(j.get("cafeteria").toString())){
                try {
                    RestTemplate restTemplate = new RestTemplate();
                    BigDecimal balance = restTemplate.getForObject(apipagos + "/creditCardBalance?ownerEmail=" + j.get("user").toString(), BigDecimal.class);
                    assert(balance.compareTo(new BigDecimal("-11")) == 1);
                    User user = new User(j.get("user").toString());
                    int orderID = Integer.parseInt(j.get("orderID").toString());
                    OrderWithUserAndDate owad = c.getOWUADFromId(user, orderID);
                    assert((balance.subtract(c.getOpenedOrderFormID(user, owad.getOrderID()).getPrice()).compareTo(new BigDecimal("-11")) == 1));
                    j.put("id", orderID);
                    j.put("payerEmail", user.getEmail());
                    j.put("concept", "Pedido realizado en " + c.getKitchenEmail());
                    j.put("date", owad.getDate().toString());
                    j.put("cost", c.getOpenedOrderFormID(user, owad.getOrderID()).getPrice());
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    HttpEntity<String> entity = new HttpEntity<String>(j.toString(), headers);
                    URI location = restTemplate.postForLocation(apipagos + "/pagos", entity, String.class);
                    assert(location != null);
                    // Email para test, se tiene que cambiar por c.getKitchenEmail() y se enviara el correo a la cafeteria pertinente
                    sendEmailService.sendEmail("javier.martinlloret@alum.uca.es", c.getOpenedOrderFormID(user, orderID).getItems().toString(), owad.getDate().toString() + " " + owad.getClient().getEmail());
                    c.endOrder(user, orderID);
                } catch (RuntimeException rE) {
                    return "{\"status\": \"fail\",\"error\": \"" + rE + "\"}";
                }
            }
        }
        return "{\"status\": \"succes\"}";
    }

    @DeleteMapping("/cancelOrder")
    public String cancelOrder(@RequestBody String payload) throws ParseException{
        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(payload);
        JSONObject j = (JSONObject)obj;
        for(Cafeteria c: ApiApplication.cafeterias){
            if(c.getKitchenEmail().equals(j.get("cafeteria").toString())){
                try {
                    User user = new User(j.get("user").toString());
                    long tempsFinal = System.currentTimeMillis();
                    long diff = c.getClosedOWUADFromId(user, Integer.parseInt(j.get("orderID").toString())).getDate().getTime() - tempsFinal;
                    if(TimeUnit.MILLISECONDS.toMinutes(diff) < 30){
                        return "{\"status\": \"fail\"}";
                    }
                    // Order o = c.getClosedOrderFromId(user, Integer.parseInt(j.get("orderID").toString()));
                    Order o = c.getClosedOrderFromId(user, Integer.parseInt(j.get("orderID").toString()));
                    JSONObject request = new JSONObject();
                    request.put("ownerEmail", user.getEmail());
                    request.put("refund", o.getPrice());
                    for(IProduct p : o.getItems().keySet()){
                        o.removeItem(p);
                    }
                    RestTemplate restTemplate = new RestTemplate();
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    HttpEntity<String> entity = new HttpEntity<String>(request.toString(), headers);
                    URI location = restTemplate.postForLocation(apipagos + "/refundBalance", entity, String.class);
                    assert(location != null);
                }catch (RuntimeException rE) {
                    return "{\"status\": \"fail\",\"error\": \"" + rE + "\"}";
                }
            }
        }
        return "{\"status\": \"succes\"}";
    }

    public static Date getDifferenceBetwenDates(Date dateInicio, Date dateFinal) {
        long milliseconds = dateFinal.getTime() - dateInicio.getTime();
        int seconds = (int) (milliseconds / 1000) % 60;
        int minutes = (int) ((milliseconds / (1000 * 60)) % 60);
        int hours = (int) ((milliseconds / (1000 * 60 * 60)) % 24);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.SECOND, seconds);
        c.set(Calendar.MINUTE, minutes);
        c.set(Calendar.HOUR_OF_DAY, hours);
        return c.getTime();
    }

}
