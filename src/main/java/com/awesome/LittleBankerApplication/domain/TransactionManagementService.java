package com.awesome.LittleBankerApplication.domain;

import com.awesome.LittleBankerApplication.models.AccountModel;
import com.awesome.LittleBankerApplication.models.TransactionModel;
import com.awesome.LittleBankerApplication.repository.AccountRepository;
import com.awesome.LittleBankerApplication.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Transaction Management Service
 * represents a service for managing transactions between two accounts. It contains methods for transferring credits from
 * one account to another, retrieving the transaction history, and searching transactions by amount transferred or IBAN.
 * The service is implemented using a transactional method to ensure that the transfer operation is atomic and synchronized methods
 * to ensure thread safety.
 */
@Service
public class TransactionManagementService {

    // A logger to log important information and events in the service.
    private static final Logger logger = LoggerFactory.getLogger(TransactionManagementService.class);

    // Private final fields to hold the transaction and account repositories.
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    // Constructor to inject the repositories.
    public TransactionManagementService(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    // The method is also transactional to ensure that the transfer operation is atomic.
    @Transactional
    public synchronized void transferCredits(String sourceIban, String targetIban, Double amountToTransfer) throws Exception {

        // Find the source and target accounts in the repository.
        Optional<AccountModel> sourceAccount = accountRepository.findByIban(sourceIban);
        Optional<AccountModel> targetAccount = accountRepository.findByIban(targetIban);

        // Log the identification of the accounts.
        logger.info("Both accounts for transaction have been identified");

        // If either the source or target account is not found, throw an exception.
        if (sourceAccount.isEmpty() || targetAccount.isEmpty()) {
            throw new Exception("Invalid source or target IBAN.");
        }

        // Log the IBANs of the source and target accounts.
        logger.info("Account one iban: " + sourceAccount.get().getIban());
        logger.info("Account one iban: " + targetAccount.get().getIban());

        // Check that the source account has enough balance for the transfer. If not, throw an exception.
        AccountModel source = sourceAccount.get();
        if (source.getAccountBalance() < amountToTransfer) {
            throw new Exception("Insufficient balance in source account.");
        }

        logger.info("Sufficient funds in source account transfer continues");

        // Update the balances of the source and target accounts
        source.setAccountBalance(source.getAccountBalance() - amountToTransfer);
        accountRepository.save(source);

        AccountModel target = targetAccount.get();
        target.setAccountBalance(target.getAccountBalance() + amountToTransfer);
        accountRepository.save(target);

        // Log the new balances of the source and target accounts
        logger.info("Account one new balance: " + targetAccount.get().getAccountBalance());
        logger.info("Account two new balance: " + sourceAccount.get().getAccountBalance());

        // Create a new transaction object and save it to the repository.
        TransactionModel transaction = new TransactionModel(new Date(), amountToTransfer, sourceIban, targetIban);
        transactionRepository.save(transaction);
    }

    // Method to retrieve the transaction history.
    public synchronized List<TransactionModel> getTransactionHistory() {
        return transactionRepository.findAll();
    }

    // Method to search transactions by amount transferred.
    public synchronized List<TransactionModel> searchTransactionsByAmount(Double amountTransferred) {
        return transactionRepository.findByAmountTransferred(amountTransferred);
    }

    // Method to search transactions by IBAN.
    public synchronized List<TransactionModel> searchTransactionsByIban(String iban) {
        return transactionRepository.findBySourceIbanOrTargetIban(iban, iban);
    }
}

