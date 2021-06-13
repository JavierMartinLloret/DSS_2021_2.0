package com.bqh_2021.Controllers;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.bqh_2021.Abstract_Factory_DAO.File_DAO.FileDayBoxDAO;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DayBoxController {

    @GetMapping("/dayBox")
    public String GetUsers(@RequestParam String cafeteria){
        FileDayBoxDAO service = new FileDayBoxDAO(cafeteria);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/YYYY");
        return "{\"dayliBox\": \"" + service.getDayBox().get(simpleDateFormat.format(new Date())).toString() + "\"}";
    }
}
