package com.awesome.LittleBankerApplication.domain;

import com.awesome.LittleBankerApplication.models.AccountModel;
import com.awesome.LittleBankerApplication.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Account Management Service represents the service layer for managing accounts.
 * It contains methods to create, remove, update,
 * and retrieve account information from the database
 * using an instance of AccountRepository.
 */
@Service
public class AccountManagementService {

    // A logger to log important information and events in the service.
    private static final Logger logger = LoggerFactory.getLogger(AccountManagementService.class);

    // The repository to access the accounts.
    @Autowired
    private AccountRepository accountRepository;

    // Creates an account in the database.
    public synchronized void createAccount(AccountModel accountModel) {
        if(accountModel.getAccountBalance() == 0.0) {

            // In case study was written when creating account add some money for transactions (if not specified).
            accountModel.setAccountBalance(1000.0);

            logger.info("Account model with iban : " + accountModel.getIban() + " was created.");
        }
        accountRepository.save(accountModel);
    }

    // Removes an account from the database.
    public synchronized void removeAccount(Integer accountId) {
        accountRepository.deleteById(Long.valueOf(accountId));

        logger.info("Successfully removed account with id :" + accountId);
    }

    // Updates the information of an account in the database.
    public synchronized void updateAccountInformation(AccountModel accountModelUpdateInfo) {
        Optional<AccountModel> accountById = accountRepository.findById(accountModelUpdateInfo.getAccountId());
        if (accountById.isPresent()) {

            logger.info("Account Id " + accountModelUpdateInfo.getAccountId() + " is being updated in db.");

            AccountModel accountModelInDb = accountById.get();
            accountModelInDb.setIban(accountModelUpdateInfo.getIban());
            accountModelInDb.setCurrency(accountModelUpdateInfo.getCurrency());
            accountModelInDb.setAccountBalance(accountModelUpdateInfo.getAccountBalance());

            accountRepository.save(accountModelInDb);
        }
    }

    // Returns all the accounts stored in the database.
    public synchronized List<AccountModel> getAllAccounts() {
        return accountRepository.findAll();
    }

    // Returns an account with a specific ID from the database.
    public Optional<AccountModel> getAccount(Integer accountId) {
        return accountRepository.findById(Long.valueOf(accountId));
    }
}

