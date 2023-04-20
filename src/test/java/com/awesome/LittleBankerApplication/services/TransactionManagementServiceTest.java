package com.awesome.LittleBankerApplication.services;

import com.awesome.LittleBankerApplication.domain.AccountManagementService;
import com.awesome.LittleBankerApplication.domain.TransactionManagementService;
import com.awesome.LittleBankerApplication.models.AccountModel;
import com.awesome.LittleBankerApplication.models.TransactionModel;
import com.awesome.LittleBankerApplication.repository.AccountRepository;
import com.awesome.LittleBankerApplication.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Transaction Management Service Test contains JUnit test cases for the TransactionManagementService class.
 */
@SpringBootTest
public class TransactionManagementServiceTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionManagementService transactionManagementService;

    @Autowired
    private AccountManagementService accountManagementService;

    // Deletes all transactions and accounts from the repository before each test case.
    @BeforeEach
    public void setup() {
        transactionRepository.deleteAll();
        accountRepository.deleteAll();
    }

    // Tests the creation of a new transaction.
    @Test
    public void createNewTransactionTest() {

        // Create two new accounts
        AccountModel account1 = new AccountModel("CZ123456789", "CZK", 1000.00);
        AccountModel account2 = new AccountModel("CZ987654321", "CZK", 2500.00);

        // Create the accounts
        accountManagementService.createAccount(account1);
        accountManagementService.createAccount(account2);

        // Create a new transaction between the two accounts
        TransactionModel transaction = new TransactionModel(new Date(), 500.00, account1.getIban(), account2.getIban(), "Test transaction");

        // Save the transaction
        transactionRepository.save(transaction);

        // Get all created transactions and check if the new transaction is among them
        List<TransactionModel> allTransactions = transactionManagementService.getTransactionHistory();
        assertEquals(1, allTransactions.size());
        TransactionModel savedTransaction = allTransactions.get(0);
        assertEquals(transaction.getAmountTransferred(), savedTransaction.getAmountTransferred());
        assertEquals(transaction.getSourceIban(), savedTransaction.getSourceIban());
        assertEquals(transaction.getTargetIban(), savedTransaction.getTargetIban());
    }

    // Tests the retrieval of all transactions.
    @Test
    public void getAllTransactionsTest() {

        // Create two new accounts
        AccountModel account1 = new AccountModel("CZ123456789", "CZK", 1000.00);
        AccountModel account2 = new AccountModel("CZ987654321", "CZK", 2500.00);
        accountManagementService.createAccount(account1);
        accountManagementService.createAccount(account2);

        // Create a list of transactions
        List<TransactionModel> transactions = new ArrayList<>();
        transactions.add(new TransactionModel(new Date(), 500.00, account1.getIban(), account2.getIban(), "Test transaction 1"));
        transactions.add(new TransactionModel(new Date(), 1000.00, account2.getIban(), account1.getIban(), "Test transaction 2"));

        // Save each transaction to the repository
        for (TransactionModel transaction : transactions) {
            transactionRepository.save(transaction);
        }

        // Retrieve all created transactions and check if their count matches the count of the original list of transactions
        List<TransactionModel> createdTransactions = transactionManagementService.getTransactionHistory();
        assertEquals(transactions.size(), createdTransactions.size());
    }

    // Test for searching transaction by amount transferred, iban or message
    @Test
    public void searchTransactionsTest() {

        // Create two new accounts
        AccountModel account1 = new AccountModel("CZ123456789", "CZK", 1000.00);
        AccountModel account2 = new AccountModel("CZ987654321", "CZK", 2500.00);
        accountManagementService.createAccount(account1);
        accountManagementService.createAccount(account2);

        // Create a list of transactions
        List<TransactionModel> transactions = new ArrayList<>();
        transactions.add(new TransactionModel(new Date(), 500.00, account1.getIban(), account2.getIban(), "Test transaction 1"));
        transactions.add(new TransactionModel(new Date(), 1000.00, account2.getIban(), account1.getIban(), "Test transaction 2"));
        transactions.add(new TransactionModel(new Date(), 1500.00, account1.getIban(), account2.getIban(), "Test transaction 3"));

        // Save each transaction to the repository
        for (TransactionModel transaction : transactions) {
            transactionRepository.save(transaction);
        }

        // Search for transactions with amount transferred equal to 1000.00
        List<TransactionModel> searchedTransactionsByAmount = transactionManagementService.searchTransactionsByAmount(1000.00);

        // Check if the count of retrieved transactions matches the expected count
        assertEquals(1, searchedTransactionsByAmount.size());

        // Check if the retrieved transaction matches the expected transaction
        TransactionModel retrievedTransactionByAmount = searchedTransactionsByAmount.get(0);
        assertEquals(1000.00, retrievedTransactionByAmount.getAmountTransferred());

        // Retrieve transactions by source IBAN
        List<TransactionModel> transactionsBySourceIban = transactionManagementService.searchTransactionsByIban(account1.getIban());
        assertEquals(3, transactionsBySourceIban.size());
        assertEquals(account1.getIban(), transactionsBySourceIban.get(0).getSourceIban());
        assertEquals(account2.getIban(), transactionsBySourceIban.get(1).getSourceIban());

        // Retrieve transactions by target IBAN
        List<TransactionModel> transactionsByTargetIban = transactionManagementService.searchTransactionsByIban(account2.getIban());
        assertEquals(3, transactionsByTargetIban.size());
        assertEquals(account2.getIban(), transactionsByTargetIban.get(0).getTargetIban());
        assertEquals(account1.getIban(), transactionsByTargetIban.get(1).getTargetIban());

        // Retrieve transactions by message
        List<TransactionModel> transactionsByMessage = transactionManagementService.searchTransactionsByMessage("Test transaction 2");
        assertEquals(1, transactionsByMessage.size());
        assertEquals("Test transaction 2", transactionsByMessage.get(0).getMessage());
    }
}