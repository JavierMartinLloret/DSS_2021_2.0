package com.bqh_2021.accessingdatamysql;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class CreditCard {
    @Id
    protected String ownerEmail;

    protected BigDecimal balance;

    

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    protected Set<Payment> paymentsArchive;

    public CreditCard(){}

    public CreditCard(String ownerEmail){
        this.ownerEmail = ownerEmail;
        this.balance = new BigDecimal("0");
        this.paymentsArchive = new HashSet<Payment>();
    }

    public void addToBalance(BigDecimal quantity)
    {
        balance = balance.add(quantity);
    }

    public void charge(BigDecimal quantity) throws RuntimeException
    {
        BigDecimal aux = new BigDecimal(balance.toString());
        if(aux.compareTo(new BigDecimal("-10.00")) == -1){
            throw new RuntimeException("Error, el cargo a la tarjeta de "+ownerEmail+" supera el saldo de la misma");
        }
        balance = balance.subtract(quantity);
    }

    public void addPaymentToArchive(Payment payment) throws RuntimeException
    {
        if(paymentsArchive.add(payment) == false)
            throw new RuntimeException("Error al añadir el nuevo ticket de pago a la tarjeta del cliente "+ownerEmail);
    }

    public String getOwnerEmail() {
        return this.ownerEmail;
    }

    public void setOwner(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public BigDecimal getBalance() {
        return this.balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Set<Payment> getPaymentsArchive() {
        return this.paymentsArchive;
    }

    public void setPaymentsArchive(Set<Payment> paymentsArchive) {
        this.paymentsArchive = paymentsArchive;
    }

}
