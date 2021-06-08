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
    protected BigDecimal price;
    
    protected String codeForPayment;
    protected boolean isAutorized; // Patrón observer
    //protected String payee;??

}
