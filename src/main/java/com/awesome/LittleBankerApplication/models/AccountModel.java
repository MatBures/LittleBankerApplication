package com.awesome.LittleBankerApplication.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import static jakarta.persistence.GenerationType.SEQUENCE;

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
