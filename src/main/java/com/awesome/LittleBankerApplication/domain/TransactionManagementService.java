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

@Service
public class TransactionManagementService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionManagementService.class);

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransactionManagementService(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional
    public synchronized void transferCredits(String sourceIban, String targetIban, Double amountToTransfer) throws Exception {

        Optional<AccountModel> sourceAccount = accountRepository.findByIban(sourceIban);
        Optional<AccountModel> targetAccount = accountRepository.findByIban(targetIban);

        logger.info("Both accounts for transaction have been identified");

        if (sourceAccount.isEmpty() || targetAccount.isEmpty()) {
            throw new Exception("Invalid source or target IBAN.");
        }

        logger.info("Account one iban: " + sourceAccount.get().getIban());
        logger.info("Account one iban: " + targetAccount.get().getIban());

        AccountModel source = sourceAccount.get();
        if (source.getAccountBalance() < amountToTransfer) {
            throw new Exception("Insufficient balance in source account.");
        }
        logger.info("Sufficient funds in source account transfer continues");

        source.setAccountBalance(source.getAccountBalance() - amountToTransfer);
        accountRepository.save(source);

        AccountModel target = targetAccount.get();
        target.setAccountBalance(target.getAccountBalance() + amountToTransfer);
        accountRepository.save(target);

        logger.info("Account one new balance: " + targetAccount.get().getAccountBalance());
        logger.info("Account two new balance: " + sourceAccount.get().getAccountBalance());

        TransactionModel transaction = new TransactionModel(new Date(), amountToTransfer, sourceIban, targetIban);
        transactionRepository.save(transaction);
    }

    @Transactional
    public synchronized List<TransactionModel> getTransactionHistory() {
        return transactionRepository.findAll();
    }
}