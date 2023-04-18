package com.awesome.LittleBankerApplication.domain;

import com.awesome.LittleBankerApplication.models.AccountModel;
import com.awesome.LittleBankerApplication.models.TransactionModel;
import com.awesome.LittleBankerApplication.repository.AccountRepository;
import com.awesome.LittleBankerApplication.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionManagementService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransactionManagementService(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional
    public synchronized void transferCredits(String sourceIban, String targetIban, double amountToTransfer) throws Exception {

        Optional<AccountModel> sourceAccount = accountRepository.findByIban(sourceIban);
        Optional<AccountModel> targetAccount = accountRepository.findByIban(targetIban);


        if (sourceAccount.isEmpty() || targetAccount.isEmpty()) {
            throw new Exception("Invalid source or target IBAN.");
        }


        AccountModel source = sourceAccount.get();
        if (source.getAccountBalance() < amountToTransfer) {
            throw new Exception("Insufficient balance in source account.");
        }


        source.setAccountBalance(source.getAccountBalance() - amountToTransfer);
        accountRepository.save(source);


        AccountModel target = targetAccount.get();
        target.setAccountBalance(target.getAccountBalance() + amountToTransfer);
        accountRepository.save(target);

    }

    @Transactional
    public synchronized List<TransactionModel> getTransactionHistory() {
        return transactionRepository.findAll();
    }
}