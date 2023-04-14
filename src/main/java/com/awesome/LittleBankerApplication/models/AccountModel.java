package com.awesome.LittleBankerApplication.models;


import jakarta.persistence.*;

@Entity
@Table(name = "accounts")
public class AccountModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int accountId;

    @Column
    private String iban;

    @Column
    private String currency;

    @Column
    private double accountBalance;

    public AccountModel() {
    }

    public AccountModel(String iban, String currency, double accountBalance) {
        this.iban = iban;
        this.currency = currency;
        this.accountBalance = accountBalance;
    }

    public String getIban() {
        return iban;
    }

    public String getCurrency() {
        return currency;
    }

    public double getAccountBalance() {
        return accountBalance;
    }
}
