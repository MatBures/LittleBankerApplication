package com.awesome.LittleBankerApplication.controllers;

import com.awesome.LittleBankerApplication.domain.TransactionManagementService;
import com.awesome.LittleBankerApplication.models.TransactionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    private final TransactionManagementService transactionService;

    public TransactionController(TransactionManagementService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transferCredits(@RequestBody TransactionModel transactionModel) {
        try {
            transactionService.transferCredits(transactionModel.getSourceIban(), transactionModel.getTargetIban(), transactionModel.getAmountToBeTransferred());
            return ResponseEntity.ok().body("Transaction completed successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/history")
    public List<TransactionModel> getTransactionHistory() {
        return transactionService.getTransactionHistory();
    }
/*
    @GetMapping("/search")
    public List<TransactionModel> searchTransactions(@RequestParam(required = false) Double amount,
                                                     @RequestParam(required = false) String iban,
                                                     @RequestParam(required = false) String message) {
        return transactionService.searchTransactions(amount, iban, message);
        */
    }
