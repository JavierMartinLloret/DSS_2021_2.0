package com.bqh_2021.Entidades.Clases;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Payment
 * @author Javier Martín-Lloret
 * @version 1.0
 * @since 4.0
 * 
 */
public class Payment{
    protected String concept;
    protected String payerEmail;
    protected Date dateOfSell;
    protected BigDecimal cost;
    
    protected String codeForPayment;

    protected boolean paied;

    public Payment(String concept, String payerEmail, Date dateOfSell, BigDecimal cost, String codeForPayment){
        this.concept = concept;
        this.payerEmail = payerEmail;
        this.dateOfSell = dateOfSell;
        this.cost = cost;
        this.codeForPayment = codeForPayment;
        this.paied = false;
    }

    //protected String payee;??

    //protected PaymentObserver observer;
    //public boolean isAutorized(){...}

    public String getCode(){
        return codeForPayment;
    }

    public boolean getPaied(){
        return paied;
    }

    public void close(){
        paied = true;
    }
}
