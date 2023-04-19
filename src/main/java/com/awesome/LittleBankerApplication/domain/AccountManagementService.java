package com.awesome.LittleBankerApplication.domain;

import com.awesome.LittleBankerApplication.models.AccountModel;
import com.awesome.LittleBankerApplication.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AccountManagementService {

    private static final Logger logger = LoggerFactory.getLogger(AccountManagementService.class);

    @Autowired
    private AccountRepository accountRepository;

    public synchronized void createAccount(AccountModel accountModel) {
        if(accountModel.getAccountBalance() == 0.0) {

            //In case study was written when creating account add some money for transactions (if not specified)
            accountModel.setAccountBalance(1000.0);
        }
        accountRepository.save(accountModel);
    }

    public synchronized void removeAccount(Integer accountId) {
        accountRepository.deleteById(Long.valueOf(accountId));
    }

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

    public synchronized List<AccountModel> getAllAccounts() {
        return accountRepository.findAll();
    }
    public Optional<AccountModel> getAccount(Integer accountId) {
        return accountRepository.findById(Long.valueOf(accountId));
    }
}

