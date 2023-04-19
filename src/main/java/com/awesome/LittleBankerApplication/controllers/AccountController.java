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

    // A logger to log important information and events in the service.
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountManagementService accountManagementService;

    // Endpoint for creating a new account
    @PostMapping(value = "/register")
    public ResponseEntity<AccountModel> registerAccount(@Valid @RequestBody AccountModel accountModel) throws Exception {

        // Log message to indicate the start of a new account registration attempt.
        logger.info("Attempt to register new account initiated, account iban: {}", accountModel.getIban());

        // Call the account management service to create the account.
        accountManagementService.createAccount(accountModel);

        // Return a successful HTTP response with the created account model.
        return ResponseEntity.ok().body(accountModel);
    }

    // Endpoint for removing an account
    @DeleteMapping(value = "/unregister/{accountId}")
    public ResponseEntity<String> removeAccount(@PathVariable Integer accountId) {
        // Call the account management service to remove the account with the given ID.
        accountManagementService.removeAccount(accountId);

        // Log message to indicate the successful removal of the account.
        logger.info("Account with ID :" + accountId + " was successfully removed.");

        // Return a successful HTTP response with a message indicating the successful removal of the account.
        return ResponseEntity.ok("Account with ID :" + accountId + " was successfully removed.");
    }

    // Endpoint for updating account information.
    @PutMapping(value = "/update/{accountId}")
    public ResponseEntity<AccountModel> updateAccountInformation(@PathVariable Integer accountId,
                                                                 @Valid @RequestBody AccountModel accountModelUpdateInfo) {
        // Set the account ID on the updated account model.
        accountModelUpdateInfo.setAccountId(Long.valueOf(accountId));

        // Call the account management service to update the account information.
        accountManagementService.updateAccountInformation(accountModelUpdateInfo);

        // Log message to indicate the successful update of the account information.
        logger.info("Account with ID: " + accountId + " was successfully updated.");

        // Return a successful HTTP response with the updated account model.
        return ResponseEntity.ok().body(accountModelUpdateInfo);
    }

    // Endpoint for retrieving account information.
    @GetMapping(value = "/get/{accountId}")
    public ResponseEntity<AccountModel> getAccount(@PathVariable Integer accountId) {

        // Call the account management service to retrieve the account with the given ID.
        Optional<AccountModel> accountModelOptional = accountManagementService.getAccount(accountId);

        // If the account is found, log message to indicate its successful retrieval.
        if (accountModelOptional.isPresent()) {
            AccountModel accountModel = accountModelOptional.get();
            logger.info("Account with ID: " + accountId + " was found.");

            // Return a successful HTTP response with the retrieved account model
            return ResponseEntity.ok().body(accountModel);
        }

        // If the account is not found, log message to indicate its absence
        logger.info("Account with ID: " + accountId + " was not found.");

        return ResponseEntity.notFound().build();
    }

    // Endpoint for retrieving all accounts
    @GetMapping(value = "/getAll")
    public ResponseEntity<List<AccountModel>> getAccounts() {

        // Call the account management service to retrieve all accounts
        List<AccountModel> accountModels = accountManagementService.getAllAccounts();

        // Return a successful HTTP response with the list of account models
        return ResponseEntity.ok().body(accountModels);
    }
    /**
     * Format the error message found by validator nicely, so it is readable by the user.
     */
    // Exception handler for validating request body
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

    // Helper method for creating error response
    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }
}

