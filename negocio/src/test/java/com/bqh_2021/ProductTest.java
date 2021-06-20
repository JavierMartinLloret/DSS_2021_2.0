package com.bqh_2021;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import com.bqh_2021.Entidades.Clases.Product;

import org.junit.Test;

public class ProductTest {
    
    @Test
    public void productStockFluctuatesCorrectly()
    {
        Product p = new Product("prod_name", new BigDecimal(10), 0, "Postres");

        assertTrue(p.getStock() == 0);

        p.addStock(10);

        assertTrue(p.getStock() == 10);

        p.removeStock(10);

        assertTrue(p.getStock() == 0);
    }

    @Test
    public void toStringFormatIsCorrect()
    {
        Product p = new Product("prod_name", new BigDecimal(10), 0, "Postres");

        assertEquals(p.toString(), (p.getName()+" ("+p.getPrice()+"€)"));
    }
}
