package com.bqh_2021.Entidades.Clases;

import java.io.Serializable;
import java.math.BigDecimal;

import com.bqh_2021.Entidades.Interfaces.IProduct;

/**
 * Product
 * @author Javier Martín-Lloret
 * @version 1.2
 * @since 1.0
 */
public class Product implements IProduct, Serializable{

    protected BigDecimal price;
    protected int stock;
    protected String name;
    protected String prodCategory;

    // Constructores
    /**
     * @author Javier Martín-Lloret
     * @version 1.3
     * @since 1.0
     * 
     * @param name name que recibe el producto registrado.
     * @param price price en € que tendrá el producto.
     * @param stock Cantidad disponible del producto en el almacen.
     * @param prodCategory Categoría en la que se incluye el producto.
     */
    public Product(String name, BigDecimal price, int stock, String prodCategory)
    {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.prodCategory = prodCategory;
    }

    public Product(){}

    public Product(String name, BigDecimal price){
        this.name = name;
        this.price = price;
    }

    @Override
    // Implementacion de métodos propios
    public int getStock()
    {
        return stock;
    }

    public void setStock(int stock)
    {
        this.stock = stock;
    }

    // Implementacion IProduct
    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public void setName(String name)
    {
        this.name = name;
    }

    /**implements IProduct
     * @author Javier Martín-Lloret
     * @version 1.1
     * @since 1.0
     * 
     * V1.1 El método muestra lo solicitado por el CLI del Hito 2
     */
    @Override
    public String toString()
    {
        return name+" ("+price+"€)";
    }

    @Override
    public BigDecimal getPrice()
    {
        return price;
    }

    @Override
    public void setPrice(BigDecimal price)
    {
        this.price = price;
    }

    public String getCategory(){
        return prodCategory;
    }

    @Override
    public void removeStock(int quantity) {
        this.stock -= quantity;
    }

    @Override
    public void addStock(int quantity) {
        this.stock += quantity;
    }
}
