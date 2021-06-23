package com.bqh_2021.Controllers;

import java.util.Set;

import com.bqh_2021.Abstract_Factory_DAO.File_DAO.FileUserDAO;
import com.bqh_2021.Entidades.Clases.User;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {
    
    private static final FileUserDAO service = new FileUserDAO();

    @GetMapping("/users")
    public Set<User> GetUsers(){
        return service.getUsers();
    }

    @PostMapping("/users")
    public String PostUsers(@RequestBody String payload) throws ParseException{
        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(payload);
        JSONObject j = (JSONObject)obj;
        Set<User> s = service.getUsers();
        try {
            s.add(new User(j.get("nickname").toString(), j.get("email").toString(), j.get("password").toString(), Boolean.parseBoolean(j.get("isAdult").toString()), true));
        } catch (RuntimeException rE) {
            return "{\"status\": \"fail\",\"error\": \"" + rE + "\"}";
        }
        service.postUsers(s);
        return "{\"status\": \"succes\"}"; 
    }

    @PutMapping("/users")
    public String PutUser(@RequestBody String payload) throws ParseException{
        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(payload);
        JSONObject j = (JSONObject)obj;
        Set<User> s = service.getUsers();
        for(User u: s){
            if(u.getEmail().equals(j.get("email").toString())){
                try {
                    u.setPassword(j.get("password").toString(), false);
                } catch (RuntimeException rE) {
                    return "{\"status\": \"fail\",\"error\": \"" + rE + "\"}";
                }
                u.setNickname(j.get("nickname").toString());
                u.setIsAdult(Boolean.parseBoolean(j.get("isAdult").toString()));
                break;
            }
        }
        service.postUsers(s);
        return "{\"status\": \"succes\"}";
    }
}
