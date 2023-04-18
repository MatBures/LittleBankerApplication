package com.awesome.LittleBankerApplication.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Date;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "transactions")
public class TransactionModel {

    @Id
    @SequenceGenerator(
            name = "transaction_sequence",
            sequenceName = "transaction_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "transaction_sequence"
    )
    private int accountId;

    @Column(name = "date_of_transaction")
    @NotBlank(message = "Please date of transaction.")
    private Date dateOfTransaction;

    @Column(name = "amount_to_be_transferred")
    @NotBlank(message = "Please specify amount to be transferred.")
    private double amountToBeTransferred;

    @Column(name = "source_Iban")
    @NotBlank(message = "Please specify source Iban.")
    private String sourceIban;

    @Column(name = "target_Iban")
    @NotBlank(message = "Please specify target Iban.")
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
