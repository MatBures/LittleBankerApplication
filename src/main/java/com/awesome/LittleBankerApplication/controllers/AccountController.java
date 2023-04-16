package com.awesome.LittleBankerApplication.controllers;

import com.awesome.LittleBankerApplication.domain.AccountManagementService;
import com.awesome.LittleBankerApplication.models.AccountModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
/**
 * Account controller
 *  Responsible for handling REST requests related to:
 *  creating a new account
 *  updating an existing account's information
 *  deleting an existing account
 *  retrieving the account balance
 *  listing all existing accounts
 *      This functionality is available mainly for the testing,
        in real environment it will not be available on the public endpoint.
 *
 */
@RestController
@RequestMapping("/account")
public class AccountController {

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
    @Autowired
    private AccountManagementService accountManagementService;

    @PostMapping(value = "/register")
    public ResponseEntity<AccountModel> createAccount(@RequestBody AccountModel accountModel) {
        accountManagementService.createAccount(accountModel);
        return ResponseEntity.ok(accountModel);
    }

    @DeleteMapping(value = "/unregister")
    public ResponseEntity<Void> removeAccount(@PathVariable Integer accountId) {
        accountManagementService.removeAccount(accountId);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/update")
    public ResponseEntity<AccountModel> updateAccountInformation(@PathVariable Integer accountId,
                                                                 @RequestBody AccountModel accountModelUpdateInfo) {
        accountModelUpdateInfo.setAccountId(Long.valueOf(accountId));
        accountManagementService.updateAccountInformation(accountModelUpdateInfo);
        return ResponseEntity.ok(accountModelUpdateInfo);
    }

    @GetMapping(value = "/get")
    public ResponseEntity<AccountModel> getAccount(@PathVariable Integer accountId) {
        Optional<AccountModel> accountModelOptional = accountManagementService.getAccount(accountId);
        if (accountModelOptional.isPresent()) {
            AccountModel accountModel = accountModelOptional.get();
            return ResponseEntity.ok(accountModel);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping(value = "/getAll")
    public ResponseEntity<List<AccountModel>> getAccounts() {
        List<AccountModel> accountModels = accountManagementService.getAllAccounts();
        return ResponseEntity.ok(accountModels);
    }
}

