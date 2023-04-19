package com.awesome.LittleBankerApplication.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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

        public TransactionModel() {
        }

        public TransactionModel(Date dateOfTransaction, Double amountTransferred, String sourceIban, String targetIban) {
            this.dateOfTransaction = dateOfTransaction;
            this.amountTransferred = amountTransferred;
            this.sourceIban = sourceIban;
            this.targetIban = targetIban;
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

        public void setAmountTransferred(double amountToBeTransferred) {
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
    }
