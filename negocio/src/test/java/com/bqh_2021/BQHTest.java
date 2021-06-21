package com.bqh_2021;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import com.bqh_2021.Entidades.Clases.BQH;
import com.bqh_2021.Entidades.Clases.Order;
import com.bqh_2021.Entidades.Clases.Product;
import com.bqh_2021.Entidades.Interfaces.IProduct;

import org.junit.Test;


public class BQHTest {
    
    @Test
    public void getProductsTest(){
        BQH s = new BQH("campus@gmail.com");
        HashMap<String, List<IProduct>> c = s.getProducts();
        assert(c != null);
        
    }

    @Test
    public void createOrderTest(){
        BQH s = new BQH("campus@gmail.com");
        int before = s.getLenOrders();
        s.createOrder();
        int after = s.getLenOrders();
        assert(before < after);
    }

    @Test
    public void addToOrderTest() throws RuntimeException{
        BQH s = new BQH("campus@gmail.com");
        int i = s.createOrder();
        Product p = new Product("bocadillo", new BigDecimal("12.20"), 10, "BOCADILLO");
        try{
            s.addToOrder(i,p,2);
        }catch(RuntimeException e){
            throw e;
        }
    }

    @Test(expected = RuntimeException.class)
    public void addToOrderTestFail() throws RuntimeException{
        BQH s = new BQH("campus@gmail.com");
        Product p = new Product("bocadillo", new BigDecimal("12.20"), 10, "BOCADILLO");
        try{
            s.addToOrder(100, p, 2);
        }catch(RuntimeException e){
            throw e;
        }
    }

    @Test
    public void removeFromOrderAllTest() throws RuntimeException{
        BQH s = new BQH("campus@gmail.com");
        int i = s.createOrder();
        Product p = new Product("bocadillo", new BigDecimal("12.20"), 10, "BOCADILLO");
        s.addToOrder(i, p, 2);
        try{
            s.removeFromOrder(i, p);
        }catch(RuntimeException e){
            throw e;
        }
    }

    @Test(expected = RuntimeException.class)
    public void removeFromOrderAllTestFail() throws RuntimeException{
        BQH s = new BQH("campus@gmail.com");
        Product p = new Product("bocadillo", new BigDecimal("12.20"), 10, "BOCADILLO");
        try{
            s.removeFromOrder(100, p);
        }catch(RuntimeException e){
            throw e;
        }
    }

    @Test
    public void removeFromOrderQtyTest() throws RuntimeException{
        BQH s = new BQH("campus@gmail.com");
        int i = s.createOrder();
        Product p = new Product("bocadillo", new BigDecimal("12.20"), 10, "BOCADILLO");
        s.addToOrder(i, p, 2);
        try{
            s.removeFromOrder(i, p, 2);
        }catch(RuntimeException e){
            throw e;
        }
    }

    @Test(expected = RuntimeException.class)
    public void removeFromOrderQtyTestFail() throws RuntimeException{
        BQH s = new BQH("campus@gmail.com");
        Product p = new Product("bocadillo", new BigDecimal("12.20"), 10, "BOCADILLO");
        try{
            s.removeFromOrder(100, p, 3);
        }catch(RuntimeException e){
            throw e;
        }
    }

    @Test(expected = RuntimeException.class)
    public void endOrderTest() throws RuntimeException{
        BQH s = new BQH("campus@gmail.com");
        int i = s.createOrder();
        s.endOrder(i);
        Product p = new Product("bocadillo", new BigDecimal("12.20"), 10, "BOCADILLO");
        try{
            s.addToOrder(i, p, 2);
        }catch(RuntimeException e){
            throw e;
        }
    }

    @Test
    public void getOrderFromIdTest() throws RuntimeException{
        BQH s = new BQH("campus@gmail.com");
        int i = s.createOrder();
        try{
            Order o = s.getOrderFromId(i);
            assert(o != null);
        }catch(RuntimeException e){
            throw e;
        }
    }

    @Test(expected = RuntimeException.class)
    public void getOrderFromIdTestFail() throws RuntimeException{
        BQH s = new BQH("campus@gmail.com");
        try{
            Order o = s.getOrderFromId(100);
            assert(o != null);
        }catch(RuntimeException e){
            throw e;
        }
    }
}
