package com.awesome.LittleBankerApplication.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

import static jakarta.persistence.GenerationType.SEQUENCE;

/**
 * Transaction Model represents a transaction object that holds information such as transaction id,
 * date of transaction, amount to be transferred, source and target iban and message.
 * It also contains getters and setters for each property of the transaction object.
 */
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
        private Long transactionId;

        @Column(name = "date_of_transaction")
        private Date dateOfTransaction;

        @Column(name = "amount_to_be_transferred")
        @NotNull(message = "Please specify amount to be transferred.")
        private Double amountTransferred;

        @Column(name = "source_Iban")
        @NotBlank(message = "Please specify source Iban.")
        private String sourceIban;

        @Column(name = "target_Iban")
        @NotBlank(message = "Please specify target Iban.")
        private String targetIban;

        @Column(name = "message")
        private String message;

        public TransactionModel() {
        }

        public TransactionModel(Date dateOfTransaction, Double amountTransferred, String sourceIban, String targetIban, String message) {
            this.dateOfTransaction = dateOfTransaction;
            this.amountTransferred = amountTransferred;
            this.sourceIban = sourceIban;
            this.targetIban = targetIban;
            this.message = message;
        }

        public Long getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(Long transactionId) {
            this.transactionId = transactionId;
        }

        public void setDateOfTransaction(Date dateOfTransaction) {
            this.dateOfTransaction = dateOfTransaction;
        }

        public void setAmountTransferred(Double amountToBeTransferred) {
            this.amountTransferred = amountToBeTransferred;
        }

        public void setSourceIban(String sourceIban) {
            this.sourceIban = sourceIban;
        }

        public void setTargetIban(String targetIban) {
            this.targetIban = targetIban;
        }

        public Date getDateOfTransaction() {
            return dateOfTransaction;
        }

        public double getAmountTransferred() {
            return amountTransferred;
        }

        public String getSourceIban() {
            return sourceIban;
        }

        public String getTargetIban() {
            return targetIban;
        }

        public String getMessage() {
        return message;
    }

        public void setMessage(String message) {
        this.message = message;
    }
}
