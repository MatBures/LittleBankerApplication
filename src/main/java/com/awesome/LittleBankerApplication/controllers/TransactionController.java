package com.awesome.LittleBankerApplication.controllers;

import com.awesome.LittleBankerApplication.domain.TransactionManagementService;
import com.awesome.LittleBankerApplication.models.TransactionModel;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);
    private final TransactionManagementService transactionService;

    public TransactionController(TransactionManagementService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transferCredits(@Valid @RequestBody TransactionModel transactionModel) {
        try {
            transactionService.transferCredits(transactionModel.getSourceIban(), transactionModel.getTargetIban(), transactionModel.getAmountTransferred());

            logger.info("Transaction with id: " + transactionModel.getTransactionId() + " was successful.");

            return ResponseEntity.ok().body("Transaction completed successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/history")
    public List<TransactionModel> getTransactionHistory() {

        return transactionService.getTransactionHistory();
    }

    @GetMapping("/search/by-iban/{iban}")
    public ResponseEntity<List<TransactionModel>> searchTransactionsByIban(@PathVariable("iban") String iban) {
        List<TransactionModel> transactions = transactionService.searchTransactionsByIban(iban);

        logger.info("Searching history of transaction by iban : " +iban);

        if (transactions.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/search/by-amount/{amountTransferred}")
    public ResponseEntity<List<TransactionModel>> searchTransactionsByAmount(@PathVariable("amountTransferred") Double amountTransferred) {
        List<TransactionModel> transactions = transactionService.searchTransactionsByAmount(amountTransferred);

        logger.info("Searching history of transaction by amountTransferred : " +amountTransferred);

        if (transactions.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(transactions);
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
