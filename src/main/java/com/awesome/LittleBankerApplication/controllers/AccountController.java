package com.awesome.LittleBankerApplication.controllers;

import com.awesome.LittleBankerApplication.domain.AccountManagementService;
import com.awesome.LittleBankerApplication.models.AccountModel;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
 *      in real environment it will not be available on the public endpoint.
 */
@RestController
@RequestMapping("/account")
public class AccountController {

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountManagementService accountManagementService;

    @PostMapping(value = "/register")
    public ResponseEntity<AccountModel> registerAccount(@Valid @RequestBody AccountModel accountModel) throws Exception {

        logger.info("Attempt to register new account initiated, account iban: {}", accountModel.getIban());

        accountManagementService.createAccount(accountModel);

        return ResponseEntity.ok().body(accountModel);
    }

    @DeleteMapping(value = "/unregister/{accountId}")
    public ResponseEntity<String> removeAccount(@PathVariable Integer accountId) {
        accountManagementService.removeAccount(accountId);

        logger.info("Account with ID :" + accountId + " was successfully removed.");

        return ResponseEntity.ok("Account with ID :" + accountId + " was successfully removed.");
    }

    @PutMapping(value = "/update/{accountId}")
    public ResponseEntity<AccountModel> updateAccountInformation(@PathVariable Integer accountId,
                                                                 @Valid @RequestBody AccountModel accountModelUpdateInfo) {
        accountModelUpdateInfo.setAccountId(Long.valueOf(accountId));
        accountManagementService.updateAccountInformation(accountModelUpdateInfo);

        logger.info("Account with ID: " + accountId + " was successfully updated.");

        return ResponseEntity.ok().body(accountModelUpdateInfo);
    }

    @GetMapping(value = "/get/{accountId}")
    public ResponseEntity<AccountModel> getAccount(@PathVariable Integer accountId) {
        Optional<AccountModel> accountModelOptional = accountManagementService.getAccount(accountId);
        if (accountModelOptional.isPresent()) {
            AccountModel accountModel = accountModelOptional.get();

            logger.info("Account with ID: " + accountId + " was found.");

            return ResponseEntity.ok().body(accountModel);
        }

        logger.info("Account with ID: " + accountId + " was not found.");

        return ResponseEntity.notFound().build();
    }

    @GetMapping(value = "/getAll")
    public ResponseEntity<List<AccountModel>> getAccounts() {
        List<AccountModel> accountModels = accountManagementService.getAllAccounts();
        return ResponseEntity.ok().body(accountModels);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<String> errors = new ArrayList<>();
        for (FieldError fieldError : fieldErrors) {
            errors.add(fieldError.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(getErrorsMap(errors));
    }

    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }
}

