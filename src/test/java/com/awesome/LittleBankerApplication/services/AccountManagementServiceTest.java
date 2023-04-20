package com.awesome.LittleBankerApplication.services;

import com.awesome.LittleBankerApplication.domain.AccountManagementService;
import com.awesome.LittleBankerApplication.models.AccountModel;
import com.awesome.LittleBankerApplication.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Account Management Service Test contains JUnit test cases for the AccountManagementService class.
 */
@SpringBootTest
public class AccountManagementServiceTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountManagementService accountManagementService;

    // Deletes all accounts from the repository before each test case.
    @BeforeEach
    public void setup() {
        accountRepository.deleteAll();
    }

    // Tests the creation of a new account.
    @Test
    public void createNewAccountTest() {

        // Create a new account
        AccountModel account = new AccountModel("CZ123456789", "CZK", 1000.00);

        // Create the account
        accountManagementService.createAccount(account);

        // Get all created accounts and check if the new account is among them
        List<AccountModel> allAccounts = accountManagementService.getAllAccounts();
        assertEquals(1, allAccounts.size());
        AccountModel savedAccount = allAccounts.get(0);
        assertEquals(account.getIban(), savedAccount.getIban());
        assertEquals(account.getAccountBalance(), savedAccount.getAccountBalance());
    }

    // Tests the retrieval of all accounts.
    @Test
    public void getAllAccountsTest() {

        // Create a list of accounts
        List<AccountModel> accounts = new ArrayList<>();
        accounts.add(new AccountModel("CZ123456789", "CZK", 1000.00));
        accounts.add(new AccountModel("CZ987654321", "CZK", 2500.00));
        accounts.add(new AccountModel("CZ456789123", "CZK", 3200.00));

        // Create each account
        accounts.forEach(accountManagementService::createAccount);

        // Retrieve all created accounts and check if their count matches the count of the original list of accounts
        List<AccountModel> createdAccounts = accountManagementService.getAllAccounts();

        assertEquals(accounts.size(), createdAccounts.size());
    }

    // Tests the deletion of an account.
    @Test
    public void deleteAccountTest() {

        // Create a new account and create it
        AccountModel account1 = new AccountModel("CZ456789123", "CZK", 3200.00);
        accountManagementService.createAccount(account1);

        // Get the ID of the created account
        Long accountId = account1.getAccountId();

        // Delete the created account by its ID
        accountManagementService.removeAccount(accountId.intValue());

        // Check that the account was deleted from the repository
        assertNull(accountRepository.findById(accountId).orElse(null));
    }
}