package com.bqh_2021;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.HashMap;

import com.bqh_2021.Entidades.Clases.Order;
import com.bqh_2021.Entidades.Clases.Product;
import com.bqh_2021.Entidades.Interfaces.IProduct;

import org.junit.Test;

public class OrderTest {
    @Test
    public void orderPriceFluctuatesCorrectly()
    {
        final int orderID = 0;
        Order order = new Order(orderID);

        Product p1 = new Product("Garbanso con aserga", new BigDecimal(5), 10, "Platos Calientes");
        Product p2 = new Product("Agua mineral", new BigDecimal(1), 10, "Bebidas Frias");

        assertEquals(order.getPrice(), BigDecimal.ZERO);

        order.addItem(p1, 2);

        final BigDecimal priceForTwoP1 = p1.getPrice().multiply(new BigDecimal(2));
        assertEquals(order.getPrice(), priceForTwoP1);

        order.addItem(p2, 1);

        assertEquals(order.getPrice(), priceForTwoP1.add(p2.getPrice()));

        order.removeItem(p1, 2);

        assertEquals(order.getPrice(), p2.getPrice());
    }

    @Test(expected = RuntimeException.class)
    public void bothWaysOfRemovingItemsWorkProperly()
    {
        final int orderID = 0;
        Order order = new Order(orderID);

        Product p1 = new Product("Garbanso con aserga", new BigDecimal(5), 10, "Platos Calientes");
        Product p2 = new Product("Agua mineral", new BigDecimal(1), 10, "Bebidas Frias");

        assertEquals(order.getItems(), new HashMap<IProduct, Integer>());

        order.addItem(p1, 2);
        order.addItem(p2, 2);

        try {
            order.removeItem(p1, 3);
        } catch (RuntimeException rE) {throw rE;}

        order.removeItem(p1, 2);

        order.removeItem(p2);

        assertEquals(order.getItems(), new HashMap<IProduct, Integer>());
    }
}
