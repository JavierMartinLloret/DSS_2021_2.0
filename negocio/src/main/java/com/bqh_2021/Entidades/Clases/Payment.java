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
public class Payment {
    protected String concept;
    protected User payer;
    protected Date dateOfSell;
    protected BigDecimal cost;
    
    protected String codeForPayment;

    //protected String payee;??

    //protected PaymentObserver observer;
    //public boolean isAutorized(){...}
}
