package com.awesome.LittleBankerApplication.models;


import jakarta.persistence.*;

@Entity
@Table(name = "accounts")
public class AccountModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @Column(name = "iban",
            unique = true)
    private String iban;

    @Column(name = "currency")
    private String currency;

    @Column(name = "accountBalance")
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

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }
}
