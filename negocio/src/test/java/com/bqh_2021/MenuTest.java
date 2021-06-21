package com.bqh_2021;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.HashMap;

import com.bqh_2021.Entidades.Clases.Menu;
import com.bqh_2021.Entidades.Clases.Product;
import com.bqh_2021.Entidades.Interfaces.IProduct;

import org.junit.Test;

public class MenuTest {
    @Test
    public void priceFluctuatesCorrectly()
    {
        String menuName = "Stanislao Figueras";
        HashMap<IProduct, Integer> inputProducts = new HashMap<IProduct, Integer>();
        float inputDiscount = 0.5f;

        // EmptyMenu
        Menu menu = new Menu(menuName, inputDiscount, inputProducts);

        assertEquals(menu.getPrice(), new BigDecimal("0.0"));

        // NonEmptyMenu
        Product p1 = new Product("prod_name_1", new BigDecimal(10), 10, "Postres");
        Product p2 = new Product("prod_name_2", new BigDecimal(5), 5, "Postres");

        inputProducts.put(p1, 2);
        inputProducts.put(p2, 1);

        menu = new Menu(menuName, inputDiscount, inputProducts);

        // Price now should be (2 * p1Price + p2Price) * 0.5. (12.5f)
        System.out.println(menu.getPrice());
        assertEquals(menu.getPrice(), p1.getPrice().multiply(new BigDecimal(2))
                                                    .add(p2.getPrice())
                                                    .divide(new BigDecimal(2)));


    }

    @Test
    public void stockFluctuatesCorrectly()
    {
        String menuName = "Manolito pies de plata";
        HashMap<IProduct, Integer> inputProducts = new HashMap<IProduct, Integer>();
        float inputDiscount = 0.5f;
        Menu menu;

        Product p1 = new Product("prod_name_1", new BigDecimal(10), 10, "Postres");
        Product p2 = new Product("prod_name_2", new BigDecimal(5), 5, "Postres");
        inputProducts.put(p1, 2);
        inputProducts.put(p2, 1);

        menu = new Menu(menuName, inputDiscount, inputProducts);

        assertEquals(5, menu.getStock());

        p2.setStock(0);

        assertEquals(0, menu.getStock());


    }


}
