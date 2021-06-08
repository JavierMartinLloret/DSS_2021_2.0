package com.bqh_2021.Requests;

import java.util.Date;
import java.util.Map;

public class OrderRequest {
    protected Map<String, Integer> order;
    protected String user, cafeteria;
    protected Date date;

    public OrderRequest(Map<String, Integer> order, String user, String cafeteria, Date date){
        this.order = order;
        this.user = user;
        this.cafeteria = cafeteria;
        this.date = date;
    }
    
    public Map<String, Integer> getOrder() {
        return order;
    }

    public void setOrder(Map<String, Integer> order){
        this.order = order;
    }

    public String getUser(){
        return user;
    }

    public void setUser(String user){
        this.user=user;
    }

    public String getCafeteria(){
        return cafeteria;
    }

    public void setCafeteria(String cafeteria){
        this.cafeteria = cafeteria;
    }

    public Date getDate(){
        return date;
    }

    public void setDate(Date date){
        this.date=date;
    }
}
