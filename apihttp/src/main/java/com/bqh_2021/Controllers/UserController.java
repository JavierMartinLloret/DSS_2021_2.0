package com.BQH_2021.API_HTTP.Controllers;

import java.util.Set;

import com.BQH_2021.API_HTTP.Requests.UserRequest;
import com.BQH_2021.Negocio.Entidades.Clases.User;
import com.BQH_2021.Negocio.Servicios.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
public class UserController {
    
    private static final UserService service = new UserService();

    @GetMapping("/users")
    public Set<User> GetUsers(){
        return service.GetUsers();
    }

    @PostMapping("/users")
    public String PostUsers(@RequestBody UserRequest user){
        Set<User> s = service.GetUsers();
        try {
            s.add(new User(user.getNickname(), user.getEmail(), user.getPassword(), user.getIsAdult(), true));
        } catch (RuntimeException rE) {
            return "{\"status\": \"fail\",\"error\": \"" + rE + "\"}";
        }
        service.PostUsers(s);
        return "{\"status\": \"succes\"}"; 
    }

    @PutMapping("/users")
    public String PutUser(@RequestBody UserRequest user){
        Set<User> s = service.GetUsers();
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
        service.PostUsers(s);
        return "{\"status\": \"succes\"}";
    }
}
