package com.bqh_2021.Entidades.Clases;

import java.math.BigDecimal;
import java.util.HashMap;

import com.bqh_2021.Entidades.Interfaces.IProduct;

/**
 * Order
 * @author Javier Martín-Lloret
 * @version 1.2
 * @since 1.0
 * 
 * V1.1 Actualizado el atributo orderItems de Lista a HashMap
 */
public class Order {
    protected int id;
    protected BigDecimal price;
    protected HashMap<IProduct, Integer> orderItems; //Integer == num de unidades del producto

    // Constructores
    /**
     * @author Javier Martín-Lloret
     * @version 1.1
     * @since 1.0
     * 
     * @param id identificador numerico de la orden
     */
    public Order(int id)
    {
        this.id = id;
        price = BigDecimal.ZERO;
        orderItems = new HashMap<IProduct, Integer>();
    }

    public Order(){}

    /**
     * @author Javier Martín-Lloret
     * @version 1.
     * @since 1.2
     * 
     * @param id identificador numerico de la orden
     * @param price precio de la orden
     * @param orderItems productos que incorpora la orden
     */
    public Order(int id, BigDecimal price, HashMap<IProduct, Integer> orderItems)
    {
        this.id = id;
        this.price = price;
        this.orderItems = orderItems;
    }

    /**
     * addItem
     * 
     * @author Javier Martín-Lloret
     * @version 1.1
     * @since 1.0
     * 
     * @param item nuevo producto o menú que se incorpora a la orden
     * @param quantity numero de items que se añaden a la orden
     * 
     * Añade un nuevo producto o menú a la orden con la que actualmente trabaja el sistema.
     */
    public void addItem(IProduct item, int quantity) throws RuntimeException
    {
        if(quantity > item.getStock()){
            throw new RuntimeException("Se está intentando asignar a una comanda más Stock del disponible del producto "+item.getName());
        }
        else{
            item.removeStock(quantity);
        }
        if(orderItems.containsKey(item)){
            quantity = orderItems.get(item)+quantity;
        }
        orderItems.put(item, quantity);
        price = price.add(item.getPrice().multiply(new BigDecimal(quantity)));
    }

    /**
     * getItems
     * 
     * @author Javier Martín-Lloret
     * @version 1.1
     * @since 1.0
     * 
     * Devuelve la colección de productos y/o menús de la comanda actual
     */
    public HashMap<IProduct, Integer> getItems()
    {
        return orderItems;
    }

    /**
     * removeItem(IProduct)
     * 
     * @author Javier Martín-Lloret
     * @version 1.1
     * @since 1.0
     * 
     * @param item producto o menú a eliminar de la orden
     * 
     * @see removeItem(IProduct, int)
     * 
     * Elimina de la orden el elemento introducido por parámetros
     */
    public void removeItem(IProduct item)
    {
        int qty = orderItems.get(item);
        item.addStock(qty);
        price = price.add(item.getPrice().multiply(new BigDecimal(-qty)));
        orderItems.remove(item);
    }

    /**
     * removeItem(IProduct, int)
     * 
     * @author Javier Martín-Lloret
     * @version 1.1
     * @since 1.1
     * 
     * @param item producto o menú a eliminar de la orden
     * @param quantity cantidad del producto a eliminar de la orden
     * 
     * @see removeItem(IProduct)
     * 
     * @throws RuntimeException Si el numero de elementos a retirar excede el de ocurrencias en la comanda
     * 
     * Retira de la orden las ocurrencias solicitadas del item concreto
     */
    public void removeItem(IProduct item, int quantity) throws RuntimeException
    {
        int actualItemUnits = orderItems.get(item);
        actualItemUnits -= quantity;
        if(actualItemUnits == 0)
            removeItem(item);
        else if(actualItemUnits > 0){
            orderItems.put(item, actualItemUnits);
            item.addStock(quantity);
            price = price.add(item.getPrice().multiply(new BigDecimal(-quantity)));
        }
        else
            throw new RuntimeException("Error, se está intentando retirar más productos de los existentes en la orden/comanda "+id);
    }

    /**
     * 
     * @author Javier Martín-Lloret
     * @version 1.0
     * @since 1.1
     * 
     * @return precio actual de la orden
     * 
     * Devuelve el precio de la comanda actualizado a sus elementos actuales
     */
    public BigDecimal getPrice()
    {
        return price;
    }
    
    public int getId()
    {
        return id;
    }
}