package com.awesome.LittleBankerApplication.repository;

import com.awesome.LittleBankerApplication.models.TransactionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * This is a Spring Data JPA repository interface for TransactionModel.
 */
public interface TransactionRepository extends JpaRepository<TransactionModel, Long> {

    // This method finds all transactions with the given amountTransferred.
    List<TransactionModel> findByAmountTransferred(Double amountTransferred);

    // This method finds all transactions with the given sourceIban or targetIban.
    List<TransactionModel> findBySourceIbanOrTargetIban(String sourceIban, String targetIban);

    // This method finds all transactions with the given message.
    List<TransactionModel> findByMessage(String message);
}