package com.bqh_2021.Controllers;

import java.util.HashMap;
import java.util.List;

import com.bqh_2021.Abstract_Factory_DAO.File_DAO.FileProductDAO;
import com.bqh_2021.Entidades.Interfaces.IProduct;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @GetMapping("/products")
    public HashMap<String, List<IProduct>> GetProducts(@RequestParam String cafeteria){
        FileProductDAO service = new FileProductDAO(cafeteria);
        return service.getProducts();
    }
}
