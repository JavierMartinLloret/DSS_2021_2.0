package com.bqh_2021.accessingdatamysql;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Payment{
    @Id
    protected Integer id;
    protected String concept;
    protected String payerEmail;
    protected Date dateOfSell;
    protected BigDecimal cost;
    @Transient
    protected String codeForPayment;
    @Transient
    protected boolean paied;

    public Payment(){}

    public Payment(Integer id, String concept, String payerEmail, Date dateOfSell, BigDecimal cost, String codeForPayment){
        this.id = id;
        this.concept = concept;
        this.payerEmail = payerEmail;
        this.dateOfSell = dateOfSell;
        this.cost = cost;
        this.codeForPayment = codeForPayment;
        this.paied = false;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getConcept() {
        return this.concept;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    public String getPayerEmail() {
        return this.payerEmail;
    }

    public void setPayerEmail(String payerEmail) {
        this.payerEmail = payerEmail;
    }

    public Date getDateOfSell() {
        return this.dateOfSell;
    }

    public void setDateOfSell(Date dateOfSell) {
        this.dateOfSell = dateOfSell;
    }

    public BigDecimal getCost() {
        return this.cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getCodeForPayment() {
        return this.codeForPayment;
    }

    public void setCodeForPayment(String codeForPayment) {
        this.codeForPayment = codeForPayment;
    }

    public boolean isPaied() {
        return this.paied;
    }

    public void setPaied(boolean paied) {
        this.paied = paied;
    }

    public void close() {
        paied = true;
    }

}

