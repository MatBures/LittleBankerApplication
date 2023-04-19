package com.awesome.LittleBankerApplication.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import static jakarta.persistence.GenerationType.SEQUENCE;

/**
 * AccountModel has three instance variables: accountId, iban, currency and accountBalance.
 * accountId is a Long value that is the primary key of the account entity in the database.
 * iban is a String value that represents the International Bank Account Number of the account.
 * currency is a String value that represents the currency of the account.
 * accountBalance is a Double value that represents the current balance of the account.
 * It also has getter and setter methods for all instance variables, allowing for easy access and modification of the account properties.
 */
@Entity
@Table(name = "accounts")
public class AccountModel {

    @Id
    @SequenceGenerator(
            name = "account_sequence",
            sequenceName = "account_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "account_sequence"
    )
    private Long accountId;

    @Column(name = "iban",
            unique = true)
    @NotBlank(message = "Please specify iban.")
    private String iban;

    @Column(name = "currency")
    @NotBlank(message = "Please specify currency.")
    private String currency;

    @Column(name = "accountBalance")
    private Double accountBalance;

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
