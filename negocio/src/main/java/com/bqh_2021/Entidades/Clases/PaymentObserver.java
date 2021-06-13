package com.bqh_2021.Entidades.Clases;


public class PaymentObserver{

    protected final Payment observable;
    protected boolean paymentIsConfirmed;

    public PaymentObserver(Payment observable)
    {
        this.observable = observable;
    }

    public void update() {
        observable.close();
    }

    
}
