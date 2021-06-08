package com.bqh_2021.Entidades.Clases;

import java.util.Date;

public class OrderWithUserAndDate {
    protected User client;
    protected Cafeteria placeOfOrder;
    protected Date orderDate;
    protected int orderID;
    // Servicios de persistencia
    // Constructores
    public OrderWithUserAndDate(){}
    /**
     * OrderWithUserAndDate
     * @author Javier Martín-Lloret
     * @version 1.0
     * @since 3.0
     * 
     * @param client
     * @param placeOfOrder
     * @param orderDate
     * @param orderID
     * 
     * @throws RuntimeException Si la fecha introducida es en el pasado, se lanzará una excepción.
     */

    public OrderWithUserAndDate(User client, Cafeteria placeOfOrder, Date orderDate, int orderID) throws RuntimeException
    {
        this.client = client;
        this.placeOfOrder = placeOfOrder;
        this.orderID = orderID;

        if(orderDate.before(new Date())) // Fecha del pedido en pasado
            throw new RuntimeException("Error, se ha solicitado un pedido para tiempo pasado");
        this.orderDate = orderDate;
    }

    public User getClient()
    {
        return client;
    }
    
    public Cafeteria getPlaceOfOrder()
    {
        return placeOfOrder;
    }

    public Date getDate()
    {
        return orderDate;
    }

    public int getOrderID()
    {
        return orderID;
    }

    @Override
    public String toString()
    {
        // De momento este método es por dependencia de la persistencia
        return client.getEmail()+" "+placeOfOrder.getKitchenEmail()+" "+orderDate.toString()+" "+orderID;
    }
}
