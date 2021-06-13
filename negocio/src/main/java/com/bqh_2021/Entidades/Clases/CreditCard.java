package com.bqh_2021.Entidades.Clases;

import java.math.BigDecimal;
import java.util.Set;

/**
 * CreditCard
 * @author Javier Martín-Lloret
 * @version 1.0
 * @since 4.0
 * 
 */
public class CreditCard {
    protected User owner;
    protected BigDecimal balance;

    protected Set<Payment> paymentsArchive;

    // Constructores

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void addToBalance(BigDecimal quantity)
    {
        balance = balance.add(quantity);
    }

    public void charge(BigDecimal quantity) throws RuntimeException
    {
        if(balance.compareTo(quantity) == -1)
            throw new RuntimeException("Error, el cargo a la tarjeta de "+owner.nickname+"supera el saldo de la misma");
        
        balance = balance.subtract(quantity);
    }

    public Set<Payment> getPaymentsArchive()
    {
        return paymentsArchive;
    }

    public void addPaymentToArchive(Payment payment) throws RuntimeException
    {
        if(paymentsArchive.add(payment) == false)
            throw new RuntimeException("Error al añadir el nuevo ticket de pago a la tarjeta del cliente "+owner.getNickname());
    }

}
