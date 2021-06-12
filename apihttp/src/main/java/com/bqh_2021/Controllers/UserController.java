package com.bqh_2021.Controllers;

import java.util.Set;

import com.bqh_2021.Abstract_Factory_DAO.File_DAO.FileUserDAO;
import com.bqh_2021.Entidades.Clases.User;
import com.bqh_2021.Requests.UserRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
public class UserController {
    
    private static final FileUserDAO service = new FileUserDAO();

    @GetMapping("/users")
    public Set<User> GetUsers(){
        return service.getUsers();
    }

    @PostMapping("/users")
    public String PostUsers(@RequestBody UserRequest user){
        Set<User> s = service.getUsers();
        try {
            s.add(new User(user.getNickname(), user.getEmail(), user.getPassword(), user.getIsAdult(), true));
        } catch (RuntimeException rE) {
            return "{\"status\": \"fail\",\"error\": \"" + rE + "\"}";
        }
        service.postUsers(s);
        return "{\"status\": \"succes\"}"; 
    }

    @PutMapping("/users")
    public String PutUser(@RequestBody UserRequest user){
        Set<User> s = service.getUsers();
        for(User u: s){
            if(u.getEmail().equals(user.getEmail())){
                try {
                    u.setPassword(user.getPassword(), false);
                } catch (RuntimeException rE) {
                    return "{\"status\": \"fail\",\"error\": \"" + rE + "\"}";
                }
                u.setNickname(user.getNickname());
                u.setIsAdult(user.getIsAdult());
                break;
            }
        }
        service.postUsers(s);
        return "{\"status\": \"succes\"}";
    }
}
