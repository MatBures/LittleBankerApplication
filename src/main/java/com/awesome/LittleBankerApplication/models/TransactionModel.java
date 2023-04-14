package com.awesome.LittleBankerApplication.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "transactions")
public class TransactionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int accountId;

    @Column
    private Date dateOfTransaction;

    @Column
    private double amountToBeTransferred;

    @Column
    private String sourceIban;

    @Column
    private String targetIban;

    public TransactionModel() {
    }

    public TransactionModel(Date dateOfTransaction, double amountToBeTransferred, String sourceIban, String targetIban) {
        this.dateOfTransaction = dateOfTransaction;
        this.amountToBeTransferred = amountToBeTransferred;
        this.sourceIban = sourceIban;
        this.targetIban = targetIban;
    }

    public Date getDateOfTransaction() {
        return dateOfTransaction;
    }

    public double getAmountToBeTransferred() {
        return amountToBeTransferred;
    }

    public String getSourceIban() {
        return sourceIban;
    }

    public String getTargetIban() {
        return targetIban;
    }
}
