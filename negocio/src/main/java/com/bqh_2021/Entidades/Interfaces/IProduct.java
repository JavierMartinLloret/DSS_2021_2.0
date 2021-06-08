package com.bqh_2021.Entidades.Interfaces;

import java.math.BigDecimal;

/**
 * IProduct
 * 
 * @author Javier Martín-Lloret
 * @version 1.1
 * @since 1.0
 */

public interface IProduct {
    String getName();
    void setName(String name);
    String toString();
    int getStock();
    void removeStock(int quantity);
    void addStock(int quantity);

    BigDecimal getPrice();
    void setPrice(BigDecimal price);
}
