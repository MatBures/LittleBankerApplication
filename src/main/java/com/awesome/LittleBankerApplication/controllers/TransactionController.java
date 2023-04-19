package com.awesome.LittleBankerApplication.controllers;

import com.awesome.LittleBankerApplication.domain.TransactionManagementService;
import com.awesome.LittleBankerApplication.models.TransactionModel;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Transaction controller
 *  Responsible for handling REST requests related to:
 *  transferring credits from one account to another
 *  retrieving transaction history
 *  searching transaction history by IBAN or amount
 */

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    // A logger to log important information and events in the service.
    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);


    // An instance of the TransactionManagementService for handling transactions.
    @Autowired
    private final TransactionManagementService transactionService;

    // Constructor for the TransactionController, injecting the TransactionManagementService instance.
    public TransactionController(TransactionManagementService transactionService) {
        this.transactionService = transactionService;
    }

    // Endpoint for transfer credits between two bank accounts.
    @PostMapping("/transfer")
    public ResponseEntity<String> transferCredits(@Valid @RequestBody TransactionModel transactionModel) {

        try {
            // Transfer the credits using the transaction service.
            transactionService.transferCredits(transactionModel.getSourceIban(), transactionModel.getTargetIban(), transactionModel.getAmountTransferred());

            // Log the success of the transaction using the logger.
            logger.info("Transaction with id: " + transactionModel.getTransactionId() + " was successful.");

            // Return a response entity with a message indicating the successful transaction.
            return ResponseEntity.ok().body("Transaction completed successfully.");

            // If there was an error during the transaction, return a response entity with a bad request status code and the error message.
          }   catch (Exception e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Endpoint for getting the transaction history.
    @GetMapping("/history")
    public List<TransactionModel> getTransactionHistory() {
        // Get the transaction history using the transaction service and return it.
        return transactionService.getTransactionHistory();
    }

    // Endpoint for search transactions by IBAN.
    @GetMapping("/search/by-iban/{iban}")
    public ResponseEntity<List<TransactionModel>> searchTransactionsByIban(@PathVariable("iban") String iban) {
        // Search for transactions with the specified IBAN using the transaction service.
        List<TransactionModel> transactions = transactionService.searchTransactionsByIban(iban);

        // Log the search operation using the logger.
        logger.info("Searching history of transaction by iban : " +iban);

        // If no transactions were found, return a response entity with a not found status code.
        if (transactions.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Otherwise, return a response entity with the list of transactions.
        return ResponseEntity.ok(transactions);
    }

    // Endpoint for search transactions by amount transferred.
    @GetMapping("/search/by-amount/{amountTransferred}")
    public ResponseEntity<List<TransactionModel>> searchTransactionsByAmount(@PathVariable("amountTransferred") Double amountTransferred) {
        // Search for transactions with the specified amount transferred using the transaction service.
        List<TransactionModel> transactions = transactionService.searchTransactionsByAmount(amountTransferred);

        // Log the search operation using the logger.
        logger.info("Searching history of transaction by amountTransferred : " +amountTransferred);

        // If no transactions were found, return a response entity with a not found status code.
        if (transactions.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Otherwise, return a response entity with the list of transactions.
        return ResponseEntity.ok(transactions);
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
