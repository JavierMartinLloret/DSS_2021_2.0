package com.bqh_2021.Entidades.Clases;

public class PaymentObserver {

    protected final Payment observable;

    public PaymentObserver(Payment observable)
    {
        this.observable = observable;
    }
}
